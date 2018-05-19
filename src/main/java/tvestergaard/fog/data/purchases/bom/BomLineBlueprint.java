package tvestergaard.fog.data.purchases.bom;

public interface BomLineBlueprint
{

    static BomLineBlueprint from(int materialId, int amount, String notes)
    {
        return new BomLineRecord(-1, null, materialId, amount, notes);
    }

    /**
     * Returns the id of the material in the bom line.
     *
     * @return the id of the material in the bom line.
     */
    int getMaterialId();

    /**
     * Returns the amount of the material needed.
     *
     * @return The amount of material needed.
     */
    int getAmount();

    /**
     * Returns the help notes on the component.
     *
     * @return The help notes on the component.
     */
    String getNotes();
}
