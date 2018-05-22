package tvestergaard.fog.data.purchases.bom;

import tvestergaard.fog.data.materials.SimpleMaterial;

import java.util.Objects;

public class BomLineRecord implements BomLine
{

    /**
     * The id of the bom line.
     */
    private final int id;

    /**
     * The material in the bom line.
     */
    private final SimpleMaterial material;

    /**
     * The id of the material in the bom line.
     */
    private final int materialId;

    /**
     * The amount of material needed.
     */
    private final int amount;

    /**
     * The help notes on the component.
     */
    private final String notes;

    /**
     * Creates a new {@link BomLineRecord}.
     *
     * @param id         The id of the bom line.
     * @param material   The material in the bom line.
     * @param materialId The id of the material in the bom line.
     * @param amount     The amount of material needed.
     * @param notes      The help notes on the component.
     */
    public BomLineRecord(int id, SimpleMaterial material, int materialId, int amount, String notes)
    {
        this.id = id;
        this.material = material;
        this.materialId = materialId;
        this.amount = amount;
        this.notes = notes;
    }

    /**
     * Returns the id of the bom line.
     *
     * @return The id of the bom line.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the material in the bom line.
     *
     * @return The material in the bom line.
     */
    @Override public SimpleMaterial getMaterial()
    {
        return material;
    }

    /**
     * Returns the id of the material in the bom line.
     *
     * @return The id of the material in the bom line.
     */
    @Override public int getMaterialId()
    {
        return materialId;
    }

    /**
     * Returns the amount of the material needed.
     *
     * @return The amount of material needed.
     */
    @Override public int getAmount()
    {
        return amount;
    }

    /**
     * Returns the help notes on the component.
     *
     * @return The help notes on the component.
     */
    @Override public String getNotes()
    {
        return notes;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BomLine)) return false;
        BomLine that = (BomLine) o;
        return getId() == that.getId() &&
                getMaterialId() == that.getMaterialId() &&
                getAmount() == that.getAmount() &&
                Objects.equals(getMaterial(), that.getMaterial()) &&
                Objects.equals(getNotes(), that.getNotes());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId(), getMaterial(), getMaterialId(), getAmount(), getNotes());
    }

    @Override public String toString()
    {
        return "BomLineRecord{" +
                "id=" + id +
                ", material=" + material +
                ", materialId=" + materialId +
                ", amount=" + amount +
                ", notes='" + notes + '\'' +
                '}';
    }
}
