package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.orders.ShedBlueprint;

public class ShedSpecification
{

    private final int width;
    private final int depth;
    private final int cladding;
    private final int flooring;

    public ShedSpecification(ShedBlueprint blueprint)
    {
        this(blueprint.getWidth(), blueprint.getDepth(), blueprint.getCladding().getId(), blueprint.getFlooring().getId());
    }

    public ShedSpecification(int width, int depth, int cladding, int flooring)
    {
        this.width = width;
        this.depth = depth;
        this.cladding = cladding;
        this.flooring = flooring;
    }

    /**
     * Returns the width of the shed.
     *
     * @return The width of the shed.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Returns the depth of the shed.
     *
     * @return The depth of the shed.
     */
    public int getDepth()
    {
        return depth;
    }

    /**
     * Returns the cladding used on the shed.
     *
     * @return The cladding used on the shed.
     */
    public int getCladding()
    {
        return cladding;
    }


    /**
     * Returns the flooring used on the shed.
     *
     * @return The flooring used on the shed.
     */
    public int getFlooring()
    {
        return flooring;
    }
}
