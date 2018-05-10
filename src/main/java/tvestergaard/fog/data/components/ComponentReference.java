package tvestergaard.fog.data.components;

public interface ComponentReference
{

    static ComponentReference from(int definition, int material)
    {
        return new ComponentRecord(definition, null, material, null);
    }

    int getDefinitionId();

    int getMaterialId();
}
