package tvestergaard.fog.logic.floorings;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
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
     * Returns the floorings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The list of the floorings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Flooring> get(Constraints<FlooringColumn> constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the floorings in the data storage.
     *
     * @return The list of the floorings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Flooring> get()
    {
        try {
            return dao.get(null);
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
    public Flooring first(Constraints<FlooringColumn> constraints)
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
     * @param name        The name of the flooring to create.
     * @param description The description of the flooring to create.
     * @param active      Whether or not the flooring can be applied to orders.
     * @return The flooring instance representing the newly created flooring.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws FlooringValidatorException When the provided information is considered invalid.
     */
    public Flooring create(String name, String description, boolean active) throws FlooringValidatorException
    {
        try {
            Set<FlooringError> reasons = validator.validate(name, description);
            if (!reasons.isEmpty())
                throw new FlooringValidatorException(reasons);
            return dao.create(FlooringBlueprint.from(name, description, active));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code flooring}.
     *
     * @param id     The id of the flooring to update.
     * @param name   The new name.
     * @param active The new active status.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws FlooringValidatorException When the provided information is considered invalid.
     */
    public boolean update(int id, String name, String description, boolean active) throws FlooringValidatorException
    {
        try {
            Set<FlooringError> reasons = validator.validate(name, description);
            if (!reasons.isEmpty())
                throw new FlooringValidatorException(reasons);
            return dao.update(FlooringUpdater.from(id, name, description, active));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
