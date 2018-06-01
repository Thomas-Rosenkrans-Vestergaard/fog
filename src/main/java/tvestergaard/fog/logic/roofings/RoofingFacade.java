package tvestergaard.fog.logic.roofings;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.components.ComponentReference;
import tvestergaard.fog.data.constraints.Constraints;
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
    private final RoofingValidator validator;

    /**
     * Creates a new {@link RoofingDAO}.
     *
     * @param dao       The {@link RoofingDAO} used to query the data storage of the application.
     * @param validator The object responsible for validating roofings.
     */
    public RoofingFacade(RoofingDAO dao, RoofingValidator validator)
    {
        this.dao = dao;
        this.validator = validator;
    }

    /**
     * Returns the roofings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The list of the roofings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Roofing> get(Constraints<RoofingColumn> constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the roofings in the data storage.
     *
     * @return The list of the roofings in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Roofing> get()
    {
        try {
            return dao.get(null);
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
    public Roofing first(Constraints<RoofingColumn> constraints)
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
     * @param name        The name of the roofing to create.
     * @param description The description of the roofing to create.
     * @param type        The type of roofing.
     * @param active      Whether or not the roofing can be applied to orders.
     * @param components  The component values.
     * @return The roofing instance representing the newly created roofing.
     * @throws ApplicationException      When an exception occurs while performing the operation.
     * @throws RoofingValidatorException When the provided roofing information is considered invalid.
     */
    public Roofing create(String name, String description, RoofingType type, boolean active, List<ComponentReference> components)
            throws RoofingValidatorException
    {
        try {
            Set<RoofingError> errors = validator.validate(name, description);
            if (!errors.isEmpty())
                throw new RoofingValidatorException(errors);
            RoofingBlueprint blueprint = RoofingBlueprint.from(name, description, active, type);
            return dao.create(blueprint, components);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code roofing}.
     *
     * @param id          The id of the roofing to update.
     * @param name        The new name.
     * @param description The new description.
     * @param active      Whether or not the roofing can be applied to orders.
     * @param components  The new components.
     * @return {@code true} if the record was updated.
     * @throws ApplicationException      When an exception occurs while performing the operation.
     * @throws RoofingValidatorException When the provided roofing information is considered invalid.
     */
    public boolean update(int id, String name, String description, boolean active, List<ComponentReference> components)
            throws RoofingValidatorException
    {
        try {
            Set<RoofingError> errors = validator.validate(name, description);
            if (!errors.isEmpty())
                throw new RoofingValidatorException(errors);
            RoofingUpdater updater = RoofingUpdater.from(id, name, description, active);
            return dao.update(updater, components);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the components definitions for the provided roofing type.
     *
     * @param roofingType The id of the roofing to return the components of.
     * @return The components for the roofing with the provided id.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<ComponentDefinition> getComponentDefinitions(RoofingType roofingType)
    {
        try {
            return dao.getComponentDefinitions(roofingType);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the component definitions for a roofing.
     *
     * @param definitions The definitions to update.
     * @return {@code true} if the component definitions was successfully updated.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public boolean update(List<ComponentDefinition> definitions)
    {
        try {
            return dao.update(definitions);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the components active for the roofing with the provided id.
     *
     * @param roofing The id of the roofing to return the active components of.
     * @return The list of the components active for the roofing with the provided id.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Component> getComponents(int roofing)
    {
        try {
            return dao.getComponents(roofing);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
