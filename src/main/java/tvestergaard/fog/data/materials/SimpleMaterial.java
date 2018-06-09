package tvestergaard.fog.data.materials;

import tvestergaard.fog.data.materials.categories.Category;
import tvestergaard.fog.data.materials.categories.IncorrectCategoryException;

public interface SimpleMaterial extends MaterialUpdater
{

    /**
     * Checks if the material is currently active.
     *
     * @return {@code true} if the material is currently active. Or {@code false} or has been replaced with a newer version.
     */
    boolean isActive();

    /**
     * Returns the category the material belongs to.
     *
     * @return The category the material belongs to.
     */
    Category getCategory();

    /**
     * Returns the specific type of category the material belongs to.
     *
     * @param category The class of the category.
     * @param <T>      The type of the category.
     * @return The category instance.
     * @throws IncorrectCategoryException When the category could not be converted.
     */
    <T extends Category> T as(Class<T> category) throws IncorrectCategoryException;
}
