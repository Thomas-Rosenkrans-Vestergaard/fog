package tvestergaard.fog.data;

public interface ComponentCollection
{

    /**
     * Returns the component associated with the provided identifier.
     *
     * @param identifier The identifier of the component to return.
     * @return The component associated with the provided identifier.
     */
    Component get(String identifier);
}
