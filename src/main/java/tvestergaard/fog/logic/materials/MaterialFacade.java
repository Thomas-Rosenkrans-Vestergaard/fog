package tvestergaard.fog.logic.materials;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.ProductionDataSource;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.materials.*;
import tvestergaard.fog.logic.ApplicationException;

import java.util.List;
import java.util.Set;

public class MaterialFacade
{

    /**
     * The {@link MaterialDAO} used to query the data storage of the application.
     */
    private final MaterialDAO dao;

    /**
     * The validator used to validate material information provided to the {@link MaterialFacade}.
     */
    private final MaterialValidator validator = new MaterialValidator();

    /**
     * Creates a new {@link MaterialDAO}.
     *
     * @param dao The {@link MaterialDAO} used to query the data storage of the application.
     */
    public MaterialFacade(MaterialDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Returns the materials in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The complete list of the materials in the data storage.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public List<Material> get(Constraint<MaterialColumn>... constraints)
    {
        try {
            return dao.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first material matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first material matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public Material first(Constraint<MaterialColumn>... constraints)
    {
        try {
            return dao.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new material into the data storage.
     *
     * @param number      The material number to create.
     * @param description The material description to create.
     * @param price       The price of the material to create.
     * @param unit        The unit size of the material to create.
     * @return The material instance representing the newly created material.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws MaterialValidatorException When the provided information is considered invalid.
     */
    public Material create(String number, String description, int price, int unit)
            throws MaterialValidatorException
    {
        try {
            Set<MaterialError> reasons = validator.validate(number, description, price, unit);
            if (!reasons.isEmpty())
                throw new MaterialValidatorException(reasons);
            return dao.create(MaterialBlueprint.from(number, description, price, unit));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided material.
     *
     * @param id          The id of the material to update.
     * @param number      The material number to update to.
     * @param description The material description to update to.
     * @param price       The price of the material to update to.
     * @param unit        The unit size of the material to update to.
     * @return {@link true} if the record was updated.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws MaterialValidatorException When the provided information is considered invalid.
     */
    public boolean update(int id, String number, String description, int price, int unit)
            throws MaterialValidatorException
    {
        try {
            Set<MaterialError> reasons = validator.validate(number, description, price, unit);
            if (!reasons.isEmpty())
                throw new MaterialValidatorException(reasons);
            return dao.update(MaterialUpdater.from(id, number, description, price, unit));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
