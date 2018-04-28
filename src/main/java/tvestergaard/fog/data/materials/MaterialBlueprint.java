package tvestergaard.fog.data.materials;

public interface MaterialBlueprint
{

    /**
     * Returns the material number.
     *
     * @return The material number.
     */
    String getNumber();

    /**
     * Returns the description of the material.
     *
     * @return The description of the material.
     */
    String getDescription();

    /**
     * Sets the description of the material.
     *
     * @param description The new description.
     */
    void setDescription(String description);

    /**
     * Returns some additional information about the usage of the material.
     *
     * @return The additional information about the usage of the material.
     */
    String getNotes();

    /**
     * Sets the additional information about the usage of the material.
     *
     * @param notes The new notes about the usage of the material.
     */
    void setNotes(String notes);

    /**
     * Returns the width of the material.
     *
     * @return The width of the material.
     */
    int getWidth();

    /**
     * Sets the width of the material.
     *
     * @param width The new width.
     */
    void setWidth(int width);

    /**
     * Returns the height of the material.
     *
     * @return The height of the material.
     */
    int getHeight();

    /**
     * Sets the height of the material.
     *
     * @param height The new height.
     */
    void setHeight(int height);

    /**
     * Returns the identifier of the usage of the material when constructing some structure.
     *
     * @return The identifier of the usage of the material when constructing some structure.
     */
    int getUsage();
}
