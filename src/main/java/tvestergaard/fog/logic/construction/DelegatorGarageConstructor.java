package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.roofing.MysqlRoofingDAO;
import tvestergaard.fog.data.roofing.RoofingDAO;

public class DelegatorGarageConstructor implements GarageConstructor
{
//
//    private final SkeletonConstructor skeletonConstructor;
//    private final RoofConstructor     roofConstructor;
//    private final RoofingDAO          roofingDAO;
//
//    public DelegatorGarageConstructor(SkeletonConstructor skeletonConstructor, RoofConstructor roofConstructor, RoofingDAO roofingDAO)
//    {
//        this.skeletonConstructor = skeletonConstructor;
//        this.roofConstructor = roofConstructor;
//        this.roofingDAO = roofingDAO;
//    }
//
//    /**
//     * Constructs the provided order into an {@link MaterialsBill}.
//     *
//     * @param constructionSpecification The order to construct.
//     * @return The resulting {@link MaterialsBill}.
//     */
//    @Override public MaterialsBill construct(ConstructionSpecification constructionSpecification)
//    {
//        try {
//            MaterialsBill bill = new MaterialsBillImpl();
//
//            // skeletonConstructor.construct(bill, skeleton, order);
//
//            RoofingComponentCollection roofing = roofingDAO.getComponentsFor(constructionSpecification.getRoofingId());
//            roofConstructor.construct(bill, constructionSpecification, roofing);
//            return bill;
//        } catch (Exception e) {
//            throw new IllegalStateException(e);
//        }
//    }
//
//    public static void main(String[] args) throws Exception
//    {
//        DelegatorGarageConstructor garageConstructor         = new DelegatorGarageConstructor(null, new TiledRoofConstructor(), new MysqlRoofingDAO(ProductionDataSource.getSource()));
//        ConstructionSpecification  constructionSpecification = new ConstructionSpecification(420, 630, 420, null, 1, 45);
//        MaterialsBill              bill                      = garageConstructor.construct(constructionSpecification);
//        for (MaterialsBillLine line : bill.getLines())
//            System.out.println(line);
//
//        System.out.println(bill.total());
//    }
}
