package tvestergaard.fog.logic.materials;

import com.google.common.collect.Multimap;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.materials.*;
import tvestergaard.fog.data.materials.attributes.AttributeDefinition;
import tvestergaard.fog.data.materials.attributes.AttributeValue;
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
     * @return The complete list of the materials in the data storage.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Material> get()
    {
        try {
            return dao.get();
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the material with the provided id.
     *
     * @param id The id of the material to return.
     * @return The ApplicationException with the provided id. {@code null} in case a material with the provided id does not exist.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    public Material get(int id)
    {
        try {
            return dao.get(id);
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
     * @param category    The category of the material to create.
     * @param attributes  The attributes of the material to create.
     * @return The material instance representing the newly created material.
     * @throws ApplicationException       When an exception occurs while performing the operation.
     * @throws MaterialValidatorException When the provided information is considered invalid.
     */
    public Material create(String number, String description, int price, int unit, int category, Set<AttributeValue> attributes)
            throws MaterialValidatorException
    {
        try {
            Set<MaterialError> reasons = validator.validate(number, description, price, unit);
            if (!reasons.isEmpty())
                throw new MaterialValidatorException(reasons);
            return dao.create(MaterialBlueprint.from(number, description, price, unit, category, attributes));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    public Material update(int id, String number, String description, int price, int unit, int category,
                           Set<AttributeValue> attributes) throws MaterialValidatorException
    {
        try {
            Set<MaterialError> reasons = validator.validate(number, description, price, unit);
            if (!reasons.isEmpty())
                throw new MaterialValidatorException(reasons);
            return dao.update(MaterialUpdater.from(id, number, description, price, unit, category, attributes));
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns a list of the attributes required for the provided category id.
     *
     * @param category The category id to return the required attributes for.
     * @return The list of the attributes required for the provided category id.
     * @throws ApplicationException When an exception occurs while performing the operation.
     */
    public Set<AttributeDefinition> getAttributesFor(int category)
    {
        try {
            return dao.getAttributesFor(category);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the complete list of the categories in the application.
     *
     * @return The complete list of the categories in the application.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Category> getCategories()
    {
        try {
            return dao.getCategories();
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns a complete list of the materials in the provided categories.
     *
     * @param categories The categories to return the materials from.
     * @return The materials in the provided categories, mapped to that category in the multimap.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public Multimap<Integer, SimpleMaterial> getByCategory(int... categories)
    {
        try {
            return dao.getByCategory(categories);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the number of materials in the data storage.
     *
     * @return The number of materials in the data storage.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public int size() throws ApplicationException
    {
        try {
            return dao.size();
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
