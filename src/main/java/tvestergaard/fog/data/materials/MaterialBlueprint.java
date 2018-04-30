package tvestergaard.fog.data.materials;

public interface MaterialBlueprint
{

    /**
     * Creates a new material blueprint using the provided information.
     *
     * @param number      The material number to specify in the blueprint.
     * @param description The material description to specify in the blueprint.
     * @param notes       The notes on the material to specify in the blueprint.
     * @param width       The width of the material to specify in the blueprint.
     * @param height      The height of the material to specify in the blueprint.
     * @param price       The price of the material to specify in the blueprint.
     * @return The resulting blueprint.
     */
    static MaterialBlueprint from(String number, String description, String notes, int width, int height, int price)
    {
        return new MaterialRecord(-1, number, description, notes, width, height, price);
    }

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
     * Returns the price of the material.
     *
     * @return The price of the material.
     */
    int getPrice();

    /**
     * Sets the price of the material.
     *
     * @param price The new price.
     */
    void setPrice(int price);
}
