package tvestergaard.fog.data.roofing;

public interface ComponentValueReference
{

    static ComponentValueReference from(int definition, int material)
    {
        return new ComponentValueRecord(definition, null, material, null);
    }

    int getDefinitionId();

    int getMaterialId();
}
