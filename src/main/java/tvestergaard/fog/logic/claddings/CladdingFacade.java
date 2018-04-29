package tvestergaard.fog.logic.claddings;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.cladding.*;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;
import java.util.Set;

public class CladdingFacade
{

    /**
     * The {@link CladdingDAO} used to query the data storage of the application.
     */
    private final CladdingDAO dao;

    /**
     * The validator used to validate cladding information provided to the {@link CladdingFacade}.
     */
    private final CladdingValidator validator = new CladdingValidator();

    /**
     * Creates a new {@link CladdingDAO}.
     *
     * @param dao The {@link CladdingDAO} used to query the data storage of the application.
     */
    public CladdingFacade(CladdingDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Creates a new {@link CladdingDAO} using the {@link MysqlCladdingDAO} for the {@link ProductionDataSource}.
     */
    public CladdingFacade()
    {
        this(new MysqlCladdingDAO(ProductionDataSource.getSource()));
    }

    /**
     * Returns the claddings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the claddings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Cladding> get(Constraint<CladdingColumn>... constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first cladding matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first cladding matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public Cladding first(Constraint<CladdingColumn>... constraints)
    {
        try {
            return dao.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new cladding into the data storage.
     *
     * @param name                The name of the cladding to create.
     * @param description         The description of the cladding to create.
     * @param pricePerSquareMeter The price per square meter of cladding (in øre).
     * @param active              Whether or not the cladding can be applied to orders.
     * @return The cladding instance representing the newly created cladding.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws CladdingValidatorException When the provided information is considered invalid.
     */
    public Cladding create(String name, String description, int pricePerSquareMeter, boolean active)
            throws CladdingValidatorException
    {
        try {
            CladdingBlueprint  blueprint = Cladding.blueprint(name, description, pricePerSquareMeter, active);
            Set<CladdingError> reasons   = validator.validate(blueprint);
            if (!reasons.isEmpty())
                throw new CladdingValidatorException(reasons);
            return dao.create(blueprint);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code cladding}.
     *
     * @param updater The cladding to update the entity in the data storage to.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws CladdingValidatorException When the provided information is considered invalid.
     */
    public boolean update(CladdingUpdater updater) throws CladdingValidatorException
    {
        try {
            Set<CladdingError> reasons = validator.validate(updater);
            if (!reasons.isEmpty())
                throw new CladdingValidatorException(reasons);
            return dao.update(updater);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}