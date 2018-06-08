package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.models.ModelDAO;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderDAO;
import tvestergaard.fog.data.roofing.RoofingDAO;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.construction.roofing.RoofingConstructor;
import tvestergaard.fog.logic.construction.roofing.TiledRoofingConstructor;
import tvestergaard.fog.logic.construction.skeleton.CAR01SkeletonConstructor;
import tvestergaard.fog.logic.construction.skeleton.SkeletonConstructor;
import tvestergaard.fog.logic.orders.UnknownOrderException;

import java.util.List;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.orders.OrderColumn.ID;

public class ConstructionFacade
{

    /**
     * The garage constructor used to construct garages.
     */
    private final DelegatorGarageConstructor garageConstructor;

    /**
     * The dao to use to access model components.
     */
    private final ModelDAO modelDAO;

    /**
     * The dao to use to access the possible roofings.
     */
    private final RoofingDAO roofingDAO;

    /**
     * The dao to use to access the orders known to the application.
     */
    private final OrderDAO orderDAO;

    /**
     * Creates a new {@link ConstructionFacade}.
     *
     * @param modelDAO   The dao to use to access model components.
     * @param roofingDAO The dao to use to access the possible roofings.
     * @param orderDAO   The dao to use to access the orders known to the application.
     */
    public ConstructionFacade(ModelDAO modelDAO, RoofingDAO roofingDAO, OrderDAO orderDAO)
    {
        SkeletonConstructor skeletonConstructor = new CAR01SkeletonConstructor();
        RoofingConstructor  roofConstructor     = new TiledRoofingConstructor();
        this.garageConstructor = new DelegatorGarageConstructor(skeletonConstructor);
        garageConstructor.register(roofConstructor);
        this.modelDAO = modelDAO;
        this.roofingDAO = roofingDAO;
        this.orderDAO = orderDAO;
    }

    /**
     * Constructs the order with the provided id.
     *
     * @param orderId The id of the order to construct.
     * @return The summary of the construction.
     * @throws ApplicationException  When a DataAccessException occurs.
     * @throws UnknownOrderException When the order with the provided id is unknown to the application.
     * @throws ConstructionException When an exception occurs while constructing the order.
     */
    public GarageConstructionSummary construct(int orderId) throws UnknownOrderException, ConstructionException
    {
        try {
            Order order = orderDAO.first(where(eq(ID, orderId)));

            if (order == null)
                throw new UnknownOrderException();

            return construct(order);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Constructs the provided order.
     *
     * @param order The order to construct.
     * @return The summary of the construction.
     * @throws ConstructionException When an exception occurs while constructing the order.
     */
    public GarageConstructionSummary construct(Order order) throws ConstructionException
    {
        try {
            ConstructionSpecification specification      = ConstructionSpecification.from(order);
            List<Component>           skeletonComponents = modelDAO.getComponents(1);
            List<Component>           roofingComponents  = roofingDAO.getComponents(order.getRoofing().getId());
            return construct(specification);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Constructs a carport using the provided specifications.
     *
     * @param specification The specifications of the carport to construct.
     * @return The result of the construction.
     * @throws ConstructionException When an exception occurs while constructing the garage.
     */
    public GarageConstructionSummary construct(ConstructionSpecification specification) throws ConstructionException
    {
        try {
            List<Component> skeletonComponents = modelDAO.getComponents(1);
            List<Component> roofingComponents  = roofingDAO.getComponents(specification.getRoofing().getId());
            return garageConstructor.construct(specification, new ComponentMap(skeletonComponents), new ComponentMap(roofingComponents));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
