package tvestergaard.fog.data;

import tvestergaard.fog.data.materials.Material;

public interface Components
{

    /**
     * Returns the component with the provided name.
     *
     * @param componentName The name of the component to return.
     * @return The component with the provided name.
     */
//    Material getComponent(String componentName);

    /**
     * Returns the component with the provided name, casted to the provided type.
     *
     * @param componentName The name of the component to return.
     * @param type          The type of the component to return.
     * @param <T>           The type of the component to return.
     * @return The component with the provided name.
     */
//    <T extends Material> T getComponent(String componentName, Class<T> type);
}
