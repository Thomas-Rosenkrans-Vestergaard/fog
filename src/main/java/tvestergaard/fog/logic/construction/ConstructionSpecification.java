package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.cladding.Cladding;

public class ConstructionSpecification
{
    private final int      width;
    private final int      length;
    private final int      height;
    private final Cladding cladding;
    private final int      roofingId;
    private final int      roofingSlope;

    public ConstructionSpecification(int width, int length, int height, Cladding cladding, int roofingId, int roofingSlope)
    {
        this.width = width;
        this.length = length;
        this.height = height;
        this.cladding = cladding;
        this.roofingId = roofingId;
        this.roofingSlope = roofingSlope;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getLength()
    {
        return this.length;
    }

    public int getHeight()
    {
        return this.height;
    }

    public Cladding getCladding()
    {
        return this.cladding;
    }

    public int getRoofingId()
    {
        return this.roofingId;
    }

    public int getRoofingSlope()
    {
        return this.roofingSlope;
    }
}
