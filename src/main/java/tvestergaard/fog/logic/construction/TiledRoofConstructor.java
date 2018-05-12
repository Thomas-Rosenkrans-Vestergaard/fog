package tvestergaard.fog.logic.construction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.roofing.RoofingType;

import static org.apache.batik.anim.dom.SVGDOMImplementation.SVG_NAMESPACE_URI;
import static tvestergaard.fog.data.roofing.RoofingType.TILED;

public class TiledRoofConstructor implements RoofingConstructor
{

    private static final String TILE                   = "ROOF_TILE";
    private static final String RIDGE_TILE             = "ROOF_RIDGE_TILE";
    private static final String RIDGE_TILE_BRACKET     = "ROOF_RIDGE_TILE_BRACKET";
    private static final String TOP_HOLDER             = "ROOF_RIDGE_LATH_HOLDER";
    private static final String TILE_BINDERS_AND_HOOKS = "ROOF_TILE_BINDER_AND_HOOKS";

    /**
     * Constructs the roof of the garage using the provided components.
     *
     * @param specification               The specifications that the roofing must satisfy.
     * @param components                  The components to use while constructing the roofing.
     * @param skeletonConstructionSummary The object containing information about the construction of the garage skeleton.
     * @return The summary containing information about the construction of the roofing.
     */
    @Override public RoofingConstructionSummary construct(ConstructionSpecification specification, Components components, SkeletonConstructionSummary skeletonConstructionSummary)
    {
        MutableMaterials materials = new MutableMaterials();

        int length    = specification.getLength();
        int width     = specification.getWidth();
        int widthHalf = width / 2;

        int roofHeight = up(Math.tan(Math.toRadians(specification.getRoofingSlope())) * widthHalf);
        int hypotenuse = up(Math.sqrt((widthHalf * widthHalf) + (roofHeight * roofHeight)));

        Component tileComponent = components.from(TILE);
        Material  tileMaterial  = tileComponent.getMaterial();

        int tileRows    = up(hypotenuse / cm(tileMaterial.getAttribute("HEIGHT_MM").getInt())) * 2;
        int tileColumns = up(length / cm(tileMaterial.getAttribute("WIDTH_MM").getInt()));

        materials.add(tileMaterial, tileRows * tileColumns, tileComponent.getDefinition().getNotes());

        calculateRidgeTiles(materials, components, specification);
        calculateTopLatheHolder(materials, components, specification);
        calculateBindersHooks(materials, components, tileRows, tileColumns);
        calculateGableCladding(materials, components, roofHeight, width);

        Component vindSkede = components.from("VINDSKEDER");
        Component sternBræt = components.from("STERN_BOARD");

        materials.add(vindSkede.getMaterial(), 2, vindSkede.getNotes());
        materials.add(sternBræt.getMaterial(), 2, vindSkede.getNotes());

        return new DefaultRoofingConstructionSummary(materials, null, null);
    }

//    private void drawLaths(Document document, Element svgRoot, ConstructionSpecification specification)
//    {
//        int thickness     = 73;
//        int length        = mm(specification.getLength());
//        int lathDistance  = 320;
//        int numberOfLaths = mm(specification.getWidth()) / 2 / lathDistance;
//        drawLath(document, svgRoot, length, thickness, (mm(specification.getWidth()) + thickness) / 2);
//
//        for (int i = 1; i <= numberOfLaths; i++)
//            drawLath(document, svgRoot, length, thickness, lathDistance * i + DRAWING_PADDING);
//
//        int width = mm(specification.getWidth());
//        for (int i = 1; i <= numberOfLaths; i++)
//            drawLath(document, svgRoot, length, thickness, width + DRAWING_PADDING - i * lathDistance - thickness / 2);
//    }
//
//    private void drawLath(Document document, Element svgRoot, int width, int height, int y)
//    {
//        Element rectangle = document.createElementNS(SVG_NAMESPACE_URI, "rect");
//        rectangle.setAttributeNS(null, "width", Integer.toString(width));
//        rectangle.setAttributeNS(null, "height", Integer.toString(height));
//        rectangle.setAttributeNS(null, "style", "fill:white;stroke-width:5;stroke:black");
//        rectangle.setAttributeNS(null, "x", Integer.toString(DRAWING_PADDING));
//        rectangle.setAttributeNS(null, "y", Integer.toString(y));
//        svgRoot.appendChild(rectangle);
//    }
//
//    private void drawSides(Document document, Element svgRoot, ConstructionSpecification specification)
//    {
//        int thickness = 20;
//
//        int currentY = DRAWING_PADDING;
//
//        for (int i = 0; i < 2; i++) {
//            Element rectangle = document.createElementNS(SVG_NAMESPACE_URI, "rect");
//            rectangle.setAttributeNS(null, "width", Integer.toString(mm(specification.getLength())));
//            rectangle.setAttributeNS(null, "height", Integer.toString(thickness));
//            rectangle.setAttributeNS(null, "style", "fill:black;stroke:black");
//            rectangle.setAttributeNS(null, "x", Integer.toString(DRAWING_PADDING));
//            rectangle.setAttributeNS(null, "y", Integer.toString(currentY));
//            svgRoot.appendChild(rectangle);
//
//            currentY = mm(specification.getWidth()) + DRAWING_PADDING - thickness;
//        }
//    }
//
//    private void drawEnds(Document document, Element svgRoot, ConstructionSpecification specification)
//    {
//        int thickness = 45;
//        int currentX  = DRAWING_PADDING;
//
//        for (int i = 0; i < 2; i++) {
//            Element rectangle = document.createElementNS(SVG_NAMESPACE_URI, "rect");
//            rectangle.setAttributeNS(null, "width", Integer.toString(thickness));
//            rectangle.setAttributeNS(null, "height", Integer.toString(mm(specification.getWidth())));
//            rectangle.setAttributeNS(null, "style", "fill:white;stroke-width:5;stroke:black");
//            rectangle.setAttributeNS(null, "x", Integer.toString(currentX));
//            rectangle.setAttributeNS(null, "y", Integer.toString(DRAWING_PADDING));
//            svgRoot.appendChild(rectangle);
//
//            currentX = mm(specification.getLength()) + DRAWING_PADDING - thickness;
//        }
//    }
//
//    private void drawRafters(Document document, Element svgRoot, ConstructionSpecification specification)
//    {
//        int thickness       = 45;
//        int numberOfRafters = mm(specification.getLength()) / 1000;
//        int rafterDistance  = mm(specification.getLength()) / numberOfRafters;
//
//        for (int i = 1; i <= numberOfRafters; i++) {
//            Element rectangle = document.createElementNS(SVG_NAMESPACE_URI, "rect");
//            rectangle.setAttributeNS(null, "width", Integer.toString(thickness));
//            rectangle.setAttributeNS(null, "height", Integer.toString(mm(specification.getWidth())));
//            rectangle.setAttributeNS(null, "style", "fill:white;stroke-width:5;stroke:black");
//            rectangle.setAttributeNS(null, "x", Integer.toString(i * (thickness + rafterDistance)));
//            rectangle.setAttributeNS(null, "y", Integer.toString(DRAWING_PADDING));
//            svgRoot.appendChild(rectangle);
//        }
//    }

    private int up(double v)
    {
        return (int) Math.ceil(v);
    }

    private double cm(int mm)
    {
        return (double) mm / 10;
    }

    /**
     * Calculates the number of ridge tiles needed for the roof.
     *
     * @param materials
     * @param components
     * @param specification
     */
    private void calculateRidgeTiles(MutableMaterials materials, Components components, ConstructionSpecification specification)
    {

        int       length             = specification.getLength();
        Component component          = components.from(RIDGE_TILE);
        Material  material           = component.getMaterial();
        int       numberOfRidgeTiles = up(length / cm(material.getAttribute("LENGTH_MM").getInt()));

        materials.add(material, numberOfRidgeTiles, component.getDefinition().getNotes());
        Component bracketComponent = components.from(RIDGE_TILE_BRACKET);
        Material  bracket          = bracketComponent.getMaterial();
        materials.add(bracket, numberOfRidgeTiles, bracketComponent.getNotes());
    }

    private void calculateTopLatheHolder(MutableMaterials materials, Components components, ConstructionSpecification specification)
    {
        Component component = components.from(TOP_HOLDER);
        Material  material  = component.getMaterial();
        int       length    = specification.getLength();
        int       amount    = up(length / cm(material.getAttribute("USE_DISTANCE_MM").getInt()));
        materials.add(material, amount, component.getNotes());
    }

    private void calculateBindersHooks(MutableMaterials materials, Components components, int tileRows, int tileColumns)
    {
        int outer = tileRows * 2 + tileColumns * 2;
        int inner = (tileRows * tileColumns - outer) / 2;

        Component component = components.from(TILE_BINDERS_AND_HOOKS);
        materials.add(component.getMaterial(), outer + inner, component.getNotes());
    }

    private void calculateGableCladding(MutableMaterials materials, Components components, int roofHeight, int roofWidth)
    {
        Component component = components.from("ROOF_GABLE_CLADDING");
        Material  material  = component.getMaterial();

        int roofHeightMM = roofHeight * 10;
        int roofWidthMM  = roofWidth * 10;

        int plankLength = material.getAttribute("LENGTH_MM").getInt();
        int plankWidth  = material.getAttribute("WIDTH_MM").getInt();

        materials.add(material, roofHeightMM * roofWidthMM / (plankLength * plankWidth) * 2, component.getNotes());
    }

    /**
     * Returns the type of roofing this object constructs.
     *
     * @return The type of roofing this object constructs.
     */
    @Override public RoofingType getType()
    {
        return TILED;
    }
}
