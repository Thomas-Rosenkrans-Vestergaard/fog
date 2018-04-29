package tvestergaard.fog.logic.floorings;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.flooring.*;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;
import java.util.Set;

public class FlooringFacade
{

    /**
     * The {@link FlooringDAO} used to query the data storage of the application.
     */
    private final FlooringDAO dao;

    /**
     * The validator used to validate flooring information provided to the {@link FlooringFacade}.
     */
    private final FlooringValidator validator = new FlooringValidator();

    /**
     * Creates a new {@link FlooringDAO}.
     *
     * @param dao The {@link FlooringDAO} used to query the data storage of the application.
     */
    public FlooringFacade(FlooringDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Creates a new {@link FlooringDAO} using the {@link MysqlFlooringDAO} for the {@link ProductionDataSource}.
     */
    public FlooringFacade()
    {
        this(new MysqlFlooringDAO(ProductionDataSource.getSource()));
    }

    /**
     * Returns the floorings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the floorings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Flooring> get(Constraint<FlooringColumn>... constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first flooring matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first flooring matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public Flooring first(Constraint<FlooringColumn>... constraints)
    {
        try {
            return dao.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new flooring into the data storage.
     *
     * @param name                The name of the flooring to create.
     * @param description         The description of the flooring to create.
     * @param pricePerSquareMeter The price per square meter of flooring (in øre).
     * @param active              Whether or not the flooring can be applied to orders.
     * @return The flooring instance representing the newly created flooring.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws FlooringValidatorException When the provided information is considered invalid.
     */
    public Flooring create(String name, String description, int pricePerSquareMeter, boolean active)
            throws FlooringValidatorException
    {
        try {
            FlooringBlueprint  blueprint = Flooring.blueprint(name, description, pricePerSquareMeter, active);
            Set<FlooringError> reasons   = validator.validate(blueprint);
            if (!reasons.isEmpty())
                throw new FlooringValidatorException(reasons);
            return dao.create(blueprint);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code flooring}.
     *
     * @param flooring The flooring to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws FlooringValidatorException When the provided information is considered invalid.
     */
    public boolean update(Flooring flooring) throws FlooringValidatorException
    {
        try {
            Set<FlooringError> reasons = validator.validate(flooring);
            if (!reasons.isEmpty())
                throw new FlooringValidatorException(reasons);
            return dao.update(flooring);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}