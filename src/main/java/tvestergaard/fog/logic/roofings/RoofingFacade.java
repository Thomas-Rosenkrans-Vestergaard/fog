package tvestergaard.fog.logic.roofings;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.roofing.*;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;
import java.util.Set;

public class RoofingFacade
{

    /**
     * The {@link RoofingDAO} used to query the data storage of the application.
     */
    private final RoofingDAO dao;

    /**
     * The validator used to validate roofing information provided to the {@link RoofingFacade}.
     */
    private final RoofingValidator validator = new RoofingValidator();

    /**
     * Creates a new {@link RoofingDAO}.
     *
     * @param dao The {@link RoofingDAO} used to query the data storage of the application.
     */
    public RoofingFacade(RoofingDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Creates a new {@link RoofingDAO} using the {@link MysqlRoofingDAO} for the {@link ProductionDataSource}.
     */
    public RoofingFacade()
    {
        this(new MysqlRoofingDAO(ProductionDataSource.getSource()));
    }

    /**
     * Returns the roofings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the roofings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Roofing> get(Constraint<RoofingColumn>... constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first roofing matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first roofing matching the provided constraints. Returns {@code null} when no constraints matches the
     * provided constraints.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public Roofing first(Constraint<RoofingColumn>... constraints)
    {
        try {
            return dao.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new roofing into the data storage.
     *
     * @param name         The name of the roofing to create.
     * @param description  The description of the roofing to create.
     * @param minimumSlope The minimum slope at which the roofing to create can be laid.
     * @param maximumSlope The maximum slope at which the roofing to create can be laid.
     * @param active       Whether or not the roofing can be applied to orders.
     * @return The roofing instance representing the newly created roofing.
     * @throws ApplicationException      When an exception occurs while performing the operation.
     * @throws RoofingValidatorException When the provided roofing information is considered invalid.
     */
    public Roofing create(String name, String description, int minimumSlope, int maximumSlope, boolean active)
            throws RoofingValidatorException
    {
        try {
            Set<RoofingError> errors = validator.validate(name, description, minimumSlope, maximumSlope);
            if (!errors.isEmpty())
                throw new RoofingValidatorException(errors);
            RoofingBlueprint blueprint = RoofingBlueprint.from(name, description, minimumSlope, maximumSlope, active);
            return dao.create(blueprint);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code roofing}.
     *
     * @param id                  The id of the roofing to update.
     * @param name                The new name.
     * @param description         The new description.
     * @param minimumSlope        The new minimum slope.
     * @param maximumSlope        The new maximum slope.
     * @param active              Whether or not the roofing can be applied to orders.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException      When an exception occurs while performing the operation.
     * @throws RoofingValidatorException When the provided roofing information is considered invalid.
     */
    public boolean update(int id, String name, String description, int minimumSlope, int maximumSlope, boolean active)
            throws RoofingValidatorException
    {
        try {
            Set<RoofingError> errors = validator.validate(name, description, minimumSlope, maximumSlope);
            if (!errors.isEmpty())
                throw new RoofingValidatorException(errors);
            RoofingUpdater updater = RoofingUpdater.from(id, name, description, minimumSlope, maximumSlope, active);
            return dao.update(updater);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
