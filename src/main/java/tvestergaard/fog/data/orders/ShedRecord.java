package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.flooring.Flooring;

import java.util.Objects;

public class ShedRecord implements Shed
{

    /**
     * The unique identifier of the shed.
     */
    private final int id;

    /**
     * The depth of the shed.
     */
    private int depth;

    /**
     * The id of the cladding used on the shed.
     */
    private int claddingId;

    /**
     * The cladding on the shed.
     */
    private Cladding cladding;

    /**
     * The id of the flooring used on the shed.
     */
    private int flooringId;

    /**
     * The flooring on the shed.
     */
    private Flooring flooring;

    /**
     * Creates a new {@link ShedRecord}.
     *
     * @param id         The unique identifier of the shed.
     * @param depth      The depth of the shed.
     * @param claddingId The id of the cladding used on the shed.
     * @param cladding   The cladding on the shed.
     * @param flooringId The id of the flooring used on the shed.
     * @param flooring   The flooring on the shed.
     */
    public ShedRecord(int id, int depth, int claddingId, Cladding cladding, int flooringId, Flooring flooring)
    {
        this.id = id;
        this.depth = depth;
        this.claddingId = claddingId;
        this.cladding = cladding;
        this.flooringId = flooringId;
        this.flooring = flooring;
    }

    /**
     * Returns the unique identifier of the shed.
     *
     * @return The unique identifier of the shed.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the depth of the shed.
     *
     * @return The depth of the shed.
     */
    @Override public int getDepth()
    {
        return depth;
    }

    /**
     * Sets the depth of the shed.
     *
     * @param depth The new depth.
     */
    @Override public void setDepth(int depth)
    {
        this.depth = depth;
    }

    /**
     * Returns the cladding used on the shed.
     *
     * @return The cladding used on the shed.
     */
    @Override public Cladding getCladding()
    {
        return cladding;
    }

    /**
     * Returns the id of the cladding used on the shed.
     *
     * @return The cladding used on the shed.
     */
    @Override public int getCladdingId()
    {
        return claddingId;
    }

    /**
     * Sets hte cladding used on the shed.
     *
     * @param cladding The new cladding.
     */
    @Override public void setCladdingId(int cladding)
    {
        this.claddingId = cladding;
    }

    /**
     * Returns the flooring used on the shed.
     *
     * @return The flooring used on the shed.
     */
    @Override public Flooring getFlooring()
    {
        return flooring;
    }

    /**
     * Returns the id of the flooring used on the shed.
     *
     * @return The flooring used on the shed.
     */
    @Override public int getFlooringId()
    {
        return flooringId;
    }

    /**
     * Sets the flooring used on the shed.
     *
     * @param flooring The new flooring.
     */
    @Override public void setFlooringId(int flooring)
    {
        this.flooringId = flooring;
    }


    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Shed)) return false;
        Shed that = (Shed) o;
        return getId() == that.getId() &&
                getDepth() == that.getDepth() &&
                Objects.equals(getCladding(), that.getCladding()) &&
                Objects.equals(getFlooring(), that.getFlooring());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }

    @Override public String toString()
    {
        return "ShedRecord{" +
                "id=" + id +
                ", depth=" + depth +
                ", claddingId=" + claddingId +
                ", cladding=" + cladding +
                ", flooringId=" + flooringId +
                ", flooring=" + flooring +
                '}';
    }
}
