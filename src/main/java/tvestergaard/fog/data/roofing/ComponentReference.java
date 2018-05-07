package tvestergaard.fog.data.roofing;

public interface ComponentReference
{

    static ComponentReference from(int definition, int material)
    {
        return new ComponentRecord(definition, null, material, null);
    }

    int getDefinitionId();

    int getMaterialId();
}
