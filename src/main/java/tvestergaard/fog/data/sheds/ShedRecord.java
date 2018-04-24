package tvestergaard.fog.data.sheds;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.flooring.Flooring;

/**
 * The default {@link Shed} implementation
 */
public class ShedRecord implements Shed
{

    /**
     * The unique identifier of the {@link Shed}.
     */
    private final int id;

    /**
     * The width of the {@link Shed}.
     */
    private int width;

    /**
     * The depth of the {@link Shed}.
     */
    private int depth;

    /**
     * The {@link Cladding} on the {@link Shed}.
     */
    private Cladding cladding;

    /**
     * The {@link Flooring} on the {@link Shed}.
     */
    private Flooring flooring;

    /**
     * Creates a new {@link ShedRecord}.
     *
     * @param id       The unique identifier of the {@link Shed}.
     * @param width    The width of the {@link Shed}.
     * @param depth    The depth of the {@link Shed}.
     * @param cladding The {@link Cladding} on the {@link Shed}.
     * @param flooring The {@link Flooring} on the {@link Shed}.
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
     * Returns the unique identifier of the {@link Shed}.
     *
     * @return The unique identifier of the {@link Shed}.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the width of the {@link Shed}.
     *
     * @return The width of the {@link Shed}.
     */
    @Override public int getWidth()
    {
        return width;
    }

    /**
     * Sets the width of the {@link Shed}.
     *
     * @param width The new width.
     */
    @Override public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * Returns the depth of the {@link Shed}.
     *
     * @return The depth of the {@link Shed}.
     */
    @Override public int getDepth()
    {
        return depth;
    }

    /**
     * Sets the depth of the {@link Shed}.
     *
     * @param depth The new depth.
     */
    @Override public void setDepth(int depth)
    {
        this.depth = depth;
    }

    /**
     * Returns the {@link Cladding} used on the {@link Shed}.
     *
     * @return The {@link Cladding} used on the {@link Shed}.
     */
    @Override public Cladding getCladding()
    {
        return cladding;
    }

    /**
     * Sets hte {@link Cladding} used on the {@link Shed}.
     *
     * @param cladding The new {@link Cladding}.
     */
    @Override public void setCladding(Cladding cladding)
    {
        this.cladding = cladding;
    }

    /**
     * Returns the {@link Flooring} used on the {@link Shed}.
     *
     * @return The {@link Flooring} used on the {@link Shed}.
     */
    @Override public Flooring getFlooring()
    {
        return flooring;
    }

    /**
     * Sets the {@link Flooring} used on the {@link Shed}.
     *
     * @param flooring The new {@link Flooring}.
     */
    @Override public void setFlooring(Flooring flooring)
    {
        this.flooring = flooring;
    }
}
