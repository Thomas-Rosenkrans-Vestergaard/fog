package tvestergaard.fog.data.materials;

public enum MaterialUsage
{
    STOLPE(0);

    private final int id;

    MaterialUsage(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }
}
