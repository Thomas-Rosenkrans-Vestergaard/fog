package tvestergaard.fog.data.roofing;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.components.ComponentDefinition;
import tvestergaard.fog.data.components.ComponentReference;
import tvestergaard.fog.data.constraints.Constraint;

import java.util.List;

public interface RoofingDAO
{

    /**
     * Returns the roofings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the roofings in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Roofing> get(Constraint<RoofingColumn>... constraints) throws DataAccessException;

    /**
     * Returns the first roofing matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first roofing matching the provided constraints. Returns {@code null} when no constraints matches the
     * provided constraints.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Roofing first(Constraint<RoofingColumn>... constraints) throws DataAccessException;

    /**
     * Inserts a new roofing into the data storage.
     *
     * @param blueprint  The roofing blueprint that contains the information necessary to create the roofing.
     * @param components The components of the roofing to create.
     * @return The roofing instance representing the newly created roofing.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    Roofing create(RoofingBlueprint blueprint, List<ComponentReference> components) throws DataAccessException;

    /**
     * Updates the entity in the data storage to match the provided {@code roofing}.
     *
     * @param updater    The roofing updater that contains the information necessary to create the roofing.
     * @param components The components in the updated roofing.
     * @return {@code true} if the record was updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(RoofingUpdater updater, List<ComponentReference> components) throws DataAccessException;

    /**
     * Updates the component definitions for a roofing.
     *
     * @param definitions The definitions to update.
     * @return {@code true} if the component definitions was successfully updated.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    boolean update(List<ComponentDefinition> definitions) throws DataAccessException;

    /**
     * Returns the components definitions for the provided roofing type.
     *
     * @param roofingType The id of the roofing to return the components of.
     * @return The components for the roofing with the provided id.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<ComponentDefinition> getComponentDefinitions(RoofingType roofingType) throws DataAccessException;

    /**
     * Returns the components active for the roofing with the provided id.
     *
     * @param roofing The id of the roofing to return the active components of.
     * @return The list of the components active for the roofing with the provided id.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    List<Component> getComponents(int roofing) throws DataAccessException;

    /**
     * Returns the number of roofings in the data storage.
     *
     * @return The number of roofings in the data storage.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    int size() throws DataAccessException;
}
