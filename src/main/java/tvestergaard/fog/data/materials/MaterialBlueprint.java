package tvestergaard.fog.data.materials;

public interface MaterialBlueprint
{

    /**
     * Creates a new material blueprint using the provided information.
     *
     * @param number      The material number to specify in the blueprint.
     * @param description The material description to specify in the blueprint.
     * @param price       The price of the material.
     * @param unit        The unit size of the material.
     * @return The resulting blueprint.
     */
    static MaterialBlueprint from(String number, String description, int price, int unit)
    {
        return new MaterialRecord(-1, number, description, price, unit);
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

    /**
     * Returns the unit size of the material.
     *
     * @return the unit size of the material.
     */
    int getUnit();

    /**
     * Sets the unit size of the material.
     *
     * @param unit The new unit size of the material.
     */
    void setUnit(int unit);
}
