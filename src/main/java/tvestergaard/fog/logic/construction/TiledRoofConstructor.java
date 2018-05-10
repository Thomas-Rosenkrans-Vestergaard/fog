package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.roofing.RoofingType;

import static tvestergaard.fog.data.roofing.RoofingType.TILED;

public class TiledRoofConstructor implements RoofConstructor
{

    private static final String TILE                   = "ROOF_TILE";
    private static final String RIDGE_TILE             = "ROOF_RIDGE_TILE";
    private static final String RIDGE_TILE_BRACKET     = "ROOF_RIDGE_TILE_BRACKET";
    private static final String TOP_HOLDER             = "ROOF_RIDGE_LATH_HOLDER";
    private static final String TILE_BINDERS_AND_HOOKS = "ROOF_TILE_BINDER_AND_HOOKS";

    /**
     * Constructs the roof of the garage using the provided components.
     *
     * @param summary       The object containing information about the construction process.
     * @param specification The specifications that the roofing must satisfy.
     * @param components    The components to use while constructing the skeleton.
     */
    @Override public void construct(MutableConstructionSummary summary, ConstructionSpecification specification, Components components)
    {
        MutableMaterialList materials = new MutableMaterialList("Tag materialer");

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

        summary.add(materials);
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
    private void calculateRidgeTiles(MutableMaterialList materials, Components components, ConstructionSpecification specification)
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

    private void calculateTopLatheHolder(MutableMaterialList materials, Components components, ConstructionSpecification specification)
    {
        Component component = components.from(TOP_HOLDER);
        Material  material  = component.getMaterial();
        int       length    = specification.getLength();
        int       amount    = up(length / cm(material.getAttribute("USE_DISTANCE_MM").getInt()));
        materials.add(material, amount, component.getNotes());
    }

    private void calculateBindersHooks(MutableMaterialList materials, Components components, int tileRows, int tileColumns)
    {
        int outer = tileRows * 2 + tileColumns * 2;
        int inner = (tileRows * tileColumns - outer) / 2;

        Component component = components.from(TILE_BINDERS_AND_HOOKS);
        materials.add(component.getMaterial(), outer + inner, component.getNotes());
    }

    private void calculateGableCladding(MutableMaterialList materials, Components components, int roofHeight, int roofWidth)
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
