package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingDAO;
import tvestergaard.fog.data.roofing.RoofingType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.roofing.RoofingColumn.ID;

public class DelegatorGarageConstructor implements GarageConstructor
{


    /**
     * The object that constructs the skeleton of the garage to construct.
     */
    private final SkeletonConstructor skeletonConstructor;

    /**
     * The registered roof constructors mapped to the type of roof they construct.
     */
    private final Map<RoofingType, RoofConstructor> roofConstructors = new HashMap<>();

    /**
     * Creates a new {@link DelegatorGarageConstructor}.
     *
     * @param skeletonConstructor The object that constructs the skeleton of the garage to construct.
     */
    public DelegatorGarageConstructor(SkeletonConstructor skeletonConstructor)
    {
        this.skeletonConstructor = skeletonConstructor;
    }

    /**
     * Registers the provided roof constructor with the {@link DelegatorGarageConstructor}.
     *
     * @param constructor The roof constructor to register.
     */
    public void register(RoofConstructor constructor)
    {
        roofConstructors.put(constructor.getType(), constructor);
    }

    /**
     * Constructs a garage from the provided specifications.
     *
     * @param specifications     The specifications for the garage to construct.
     * @param skeletonComponents The components used to construct the skeleton of the garage.
     * @param roofingComponents  The components used to construct the roofing of the garage.
     * @return The bill of materials for the generated garage.
     */
    @Override public MaterialList construct(ConstructionSpecification specifications,
                                            Components skeletonComponents,
                                            Components roofingComponents)
    {
        MutableMaterialList materials = new MutableMaterialList();

        Roofing         roofing         = specifications.getRoofing();
        RoofingType     roofingType     = roofing.getType();
        RoofConstructor roofConstructor = roofConstructors.get(roofingType);

        if (roofConstructor == null)
            throw new IllegalStateException("No constructor able to construct " + roofingType.name());

        skeletonConstructor.construct(materials, skeletonComponents, specifications);
        roofConstructor.construct(materials, roofingComponents, specifications);

        return materials;
    }

    public static void main(String[] args) throws Exception
    {
        RoofingDAO roofingDAO = new MysqlRoofingDAO(ProductionDataSource.getSource());
        Roofing    roofing    = roofingDAO.first(where(eq(ID, 1)));

        ConstructionFacade        constructionFacade        = new ConstructionFacade();
        ConstructionSpecification constructionSpecification = new ConstructionSpecification(420, 630, 420, null, roofing, 45);
        MaterialList list = constructionFacade.construct(
                constructionSpecification,
                new Components(new ArrayList<>()),
                new Components(roofingDAO.getComponentsFor(roofing.getId())));

        for (MaterialLine line : list.getLines())
            System.out.println(line);

        System.out.println(list.getTotal());
    }
}
