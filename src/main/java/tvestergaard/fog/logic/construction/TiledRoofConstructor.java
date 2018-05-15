package tvestergaard.fog.logic.construction;

import org.w3c.dom.Document;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.roofing.RoofingType;

import static tvestergaard.fog.data.roofing.RoofingType.TILED;

public class TiledRoofConstructor extends DrawingUtilities implements RoofingConstructor
{

    private static final String TILE                   = "ROOF_TILE";
    private static final String RIDGE_TILE             = "ROOF_RIDGE_TILE";
    private static final String RIDGE_TILE_BRACKET     = "ROOF_RIDGE_TILE_BRACKET";
    private static final String TOP_HOLDER             = "ROOF_RIDGE_LATH_HOLDER";
    private static final String TILE_BINDERS_AND_HOOKS = "ROOF_TILE_BINDER_AND_HOOKS";


    protected static final int SIDE_OVERHANG_MM = 250;
    protected static final int END_OVERHANG_MM  = 500;
    protected static final int PADDING          = 1000;

    protected int             length;
    protected int             outerLength;
    protected int             width;
    protected int             outerWidth;

    /**
     * Constructs the roof of the garage using the provided components.
     *
     * @param specification               The specifications that the roofing must satisfy.
     * @param components                  The components to use while constructing the roofing.
     * @param skeletonConstructionSummary The object containing information about the construction of the garage skeleton.
     * @return The summary containing information about the construction of the roofing.
     */
    @Override public RoofingConstructionSummary construct(ConstructionSpecification specification,
                                                          ComponentMap components,
                                                          SkeletonConstructionSummary skeletonConstructionSummary)
    {
        this.length = mm(specification.getLength());
        this.outerLength = length + END_OVERHANG_MM * 2;
        this.width = mm(specification.getWidth());
        this.outerWidth = width + SIDE_OVERHANG_MM * 2;

        Materials materials    = calculateMaterials(specification, components);
        Document  skeletonView = createDocument(this.outerLength + PADDING * 2, this.outerWidth + PADDING * 2);
        Document  tiledView    = createDocument(this.outerLength + PADDING * 2, this.outerWidth + PADDING * 2);
        Document  gableView    = null; //drawGableView(specification);

        return new DefaultRoofingConstructionSummary(materials,
                new DocumentConstructionDrawing(skeletonView),
                new DocumentConstructionDrawing(tiledView),
                new DocumentConstructionDrawing(gableView));
    }

    private Document drawTiledView(ConstructionSpecification specification, ComponentMap components, Document skeletonView)
    {
        Document document = createDocument(this.outerLength + PADDING * 2, this.outerWidth + PADDING * 2);
        copy(skeletonView, document);

        tiles(document, specification, components);
        drawEnds(document, specification);

        return document;
    }

    private void tiles(Document document, ConstructionSpecification specification, ComponentMap components)
    {
        int tileHeight      = 420;
        int tileWidth       = 330;
        int ridgeTileLength = 420;
        int ridgeTileHeight = 252;
        int lathDistance    = 320;
        int thickness       = 73;
        int mid             = PADDING + SIDE_OVERHANG_MM + this.width / 2 - thickness / 2;

        int columns = this.outerLength / tileWidth;
        int rows    = this.outerWidth / 2 / lathDistance;

        for (int c = 0; c < columns; c++) {
            for (int r = 0; r < rows; r++) {
                rect(document, tileWidth, tileHeight, PADDING + tileWidth * c, mid + lathDistance * r);
            }
        }

        int restBottom = this.outerWidth / 2 - rows * lathDistance + thickness / 2;
        for (int c = 0; c < columns; c++)
            rect(document, tileWidth, restBottom, PADDING + tileWidth * c, mid + lathDistance * rows);

        int restRight = this.outerLength - columns * tileWidth;
        for (int r = 0; r < rows; r++)
            rect(document, restRight, tileHeight, PADDING + tileWidth * columns, mid + lathDistance * r);

        rect(document, restRight, restBottom, PADDING + tileWidth * columns, mid + lathDistance * rows);

        int numberOfRidgeTiles = this.outerLength / ridgeTileLength;
        for (int c = 0; c < numberOfRidgeTiles; c++)
            rect(document, ridgeTileLength, ridgeTileHeight, PADDING + ridgeTileLength * c, mid - ridgeTileHeight / 2);

        int restRidgeTileLength = outerLength - numberOfRidgeTiles * ridgeTileLength;
        rect(document, restRidgeTileLength, ridgeTileHeight, PADDING + restRidgeTileLength * (numberOfRidgeTiles + 1), mid - ridgeTileHeight / 2);
    }

    /**
     * Draws the aeriel view of the skeleton.
     *
     * @param specification               The specifications that the roofing must satisfy.
     * @param components                  The components to use while constructing the roofing.
     * @param skeletonConstructionSummary The object containing information about the construction of the garage skeleton.
     * @return The resulting drawing.
     */
    private Document drawSkeletonView(ConstructionSpecification specification, ComponentMap components, SkeletonConstructionSummary skeletonConstructionSummary)
    {
        Document document = createDocument(this.outerLength + PADDING * 2, this.outerWidth + PADDING * 2);
        copy(skeletonConstructionSummary.getAerialView().getDocument(), document);

        drawSides(document, specification);
        drawRafters(document, specification);
        drawLaths(document, specification);
        drawEnds(document, specification);

        return document;
    }

    private Materials calculateMaterials(ConstructionSpecification specification, ComponentMap components)
    {
        MutableMaterials materials = new MutableMaterials();
        int              length    = specification.getLength();
        int              width     = specification.getWidth();
        int              widthHalf = width / 2;

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
        Component sternBoard = components.from("STERN_BOARD");

        materials.add(vindSkede.getMaterial(), 2, vindSkede.getNotes());
        materials.add(sternBoard.getMaterial(), 2, vindSkede.getNotes());
        return materials;
    }

    private void drawLaths(Document document, ConstructionSpecification specification)
    {
        int thickness     = 73;
        int lathDistance  = 320;
        int numberOfLaths = outerWidth / 2 / lathDistance;
        int mid           = PADDING + SIDE_OVERHANG_MM + width / 2 - thickness / 2;
        rect(document, this.outerLength, thickness, PADDING, mid);

        for (int i = 1; i <= numberOfLaths; i++)
            rect(document, this.outerLength, thickness, PADDING, mid - lathDistance * i);

        for (int i = 1; i <= numberOfLaths; i++)
            rect(document, this.outerLength, thickness, PADDING, mid + i * lathDistance);
    }

    private void drawSides(Document document, ConstructionSpecification specification)
    {
        int thickness = 20;

        filledRect(document, this.outerLength, thickness, PADDING, PADDING);
        filledRect(document, this.outerLength, thickness, PADDING, PADDING + this.outerWidth);
    }

    private void drawEnds(Document document, ConstructionSpecification specification)
    {
        int thickness = 45;

        rect(document, thickness, this.outerWidth, PADDING, PADDING);
        rect(document, thickness, this.outerWidth, this.outerLength + PADDING - thickness, PADDING);
        filledRect(document, thickness, 20, PADDING, PADDING + this.outerWidth / 2);
        filledRect(document, thickness, 20, this.outerLength + PADDING - thickness, PADDING + this.outerWidth / 2);
    }

    private void drawRafters(Document document, ConstructionSpecification specification)
    {
        int thickness       = 45;
        int numberOfRafters = this.outerLength / 1000;
        int rafterDistance  = this.outerLength / numberOfRafters;

        for (int i = 1; i < numberOfRafters; i++)
            rect(document, thickness, this.outerWidth, PADDING + i * (thickness + rafterDistance), PADDING);
    }

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
    private void calculateRidgeTiles(MutableMaterials materials, ComponentMap components, ConstructionSpecification specification)
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

    private void calculateTopLatheHolder(MutableMaterials materials, ComponentMap components, ConstructionSpecification specification)
    {
        Component component = components.from(TOP_HOLDER);
        Material  material  = component.getMaterial();
        int       length    = specification.getLength();
        int       amount    = up(length / cm(material.getAttribute("USE_DISTANCE_MM").getInt()));
        materials.add(material, amount, component.getNotes());
    }

    private void calculateBindersHooks(MutableMaterials materials, ComponentMap components, int tileRows, int tileColumns)
    {
        int outer = tileRows * 2 + tileColumns * 2;
        int inner = (tileRows * tileColumns - outer) / 2;

        Component component = components.from(TILE_BINDERS_AND_HOOKS);
        materials.add(component.getMaterial(), outer + inner, component.getNotes());
    }

    private void calculateGableCladding(MutableMaterials materials, ComponentMap components, int roofHeight, int roofWidth)
    {
        Component component = components.from("ROOF_GABLE_CLADDING");
        Material  material  = component.getMaterial();

        int roofHeightMM = roofHeight * 10;
        int roofWidthMM  = roofWidth * 10;

//        int plankLength = material.getAttribute("LENGTH_MM").getInt();
//        int plankWidth  = material.getAttribute("WIDTH_MM").getInt();

        materials.add(material, /*roofHeightMM * roofWidthMM / (plankLength * plankWidth) * 2*/ -1, component.getNotes());
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
