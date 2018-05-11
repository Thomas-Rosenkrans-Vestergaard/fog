package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.models.ModelDAO;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.roofing.RoofingDAO;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;

public class ConstructionFacade
{

    /**
     * The garage constructor used to construct garages.
     */
    private final DelegatorGarageConstructor garageConstructor;
    private final ModelDAO                   modelDAO;
    private final RoofingDAO                 roofingDAO;

    /**
     * Creates a new {@link ConstructionFacade}.
     */
    public ConstructionFacade(ModelDAO modelDAO, RoofingDAO roofingDAO)
    {
        SkeletonConstructor skeletonConstructor = new CAR01SkeletonConstructor();
        RoofConstructor     roofConstructor     = new TiledRoofConstructor();
        this.garageConstructor = new DelegatorGarageConstructor(skeletonConstructor);
        garageConstructor.register(roofConstructor);
        this.modelDAO = modelDAO;
        this.roofingDAO = roofingDAO;
    }

    /**
     * Constructs a garage from the provided specifications.
     *
     * @param order The specifications for the garage to construct.
     * @return The summary of the construction.
     * @throws ApplicationException When a DataAccessException occurs.
     */
    public ConstructionSummary construct(Order order)
    {
        try {
            ConstructionSpecification specification      = ConstructionSpecification.from(order);
            List<Component>           skeletonComponents = modelDAO.getComponents(1);
            List<Component>           roofingComponents  = roofingDAO.getComponents(order.getRoofing().getId());
            return garageConstructor.construct(specification, new Components(skeletonComponents), new Components(roofingComponents));

        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
