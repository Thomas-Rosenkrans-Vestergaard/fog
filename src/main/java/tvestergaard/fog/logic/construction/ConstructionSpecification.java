package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.roofing.Roofing;

public class ConstructionSpecification
{
    private final int      width;
    private final int      length;
    private final int      height;
    private final Cladding cladding;
    private final Roofing  roofing;
    private final int      roofingSlope;

    public ConstructionSpecification(int width, int length, int height, Cladding cladding, Roofing roofing, int roofingSlope)
    {
        this.width = width;
        this.length = length;
        this.height = height;
        this.cladding = cladding;
        this.roofing = roofing;
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

    public Roofing getRoofing()
    {
        return this.roofing;
    }

    public int getRoofingSlope()
    {
        return this.roofingSlope;
    }
}
