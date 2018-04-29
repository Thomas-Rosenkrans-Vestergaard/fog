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
     * The width of the shed.
     */
    private int width;

    /**
     * The depth of the shed.
     */
    private int depth;

    /**
     * The cladding on the shed.
     */
    private Cladding cladding;

    /**
     * The flooring on the shed.
     */
    private Flooring flooring;

    /**
     * Creates a new {@link ShedRecord}.
     *
     * @param id       The unique identifier of the shed.
     * @param width    The width of the shed.
     * @param depth    The depth of the shed.
     * @param cladding The cladding on the shed.
     * @param flooring The flooring on the shed.
     */
    public ShedRecord(int id, int width, int depth, Cladding cladding, Flooring flooring)
    {
        this.id = id;
        this.width = width;
        this.depth = depth;
        this.cladding = cladding;
        this.flooring = flooring;
    }

    /**
     * Returns the unique identifier of the shed.
     *
     * @return The unique identifier of the shed.
     */
    @Override
    public int getId()
    {
        return id;
    }

    /**
     * Returns the width of the shed.
     *
     * @return The width of the shed.
     */
    @Override
    public int getWidth()
    {
        return width;
    }

    /**
     * Sets the width of the shed.
     *
     * @param width The new width.
     */
    @Override
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * Returns the depth of the shed.
     *
     * @return The depth of the shed.
     */
    @Override
    public int getDepth()
    {
        return depth;
    }

    /**
     * Sets the depth of the shed.
     *
     * @param depth The new depth.
     */
    @Override
    public void setDepth(int depth)
    {
        this.depth = depth;
    }

    /**
     * Returns the cladding used on the shed.
     *
     * @return The cladding used on the shed.
     */
    @Override
    public Cladding getCladding()
    {
        return cladding;
    }

    /**
     * Sets hte cladding used on the shed.
     *
     * @param cladding The new cladding.
     */
    @Override
    public void setCladding(Cladding cladding)
    {
        this.cladding = cladding;
    }

    /**
     * Returns the flooring used on the shed.
     *
     * @return The flooring used on the shed.
     */
    @Override
    public Flooring getFlooring()
    {
        return flooring;
    }

    /**
     * Sets the flooring used on the shed.
     *
     * @param flooring The new flooring.
     */
    @Override
    public void setFlooring(Flooring flooring)
    {
        this.flooring = flooring;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Shed)) return false;
        Shed that = (Shed) o;
        return getId() == that.getId() &&
                getWidth() == that.getWidth() &&
                getDepth() == that.getDepth() &&
                Objects.equals(getCladding(), that.getCladding()) &&
                Objects.equals(getFlooring(), that.getFlooring());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId());
    }
}
