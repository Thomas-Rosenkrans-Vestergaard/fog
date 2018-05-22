package tvestergaard.fog.data.models;

public interface ModelUpdater
{

    /**
     * Creates a new {@link ModelUpdater} from the provided information.
     *
     * @param id   The id of the model.
     * @param name The name of the model.
     * @return The resulting {@link ModelUpdater}.
     */
    static ModelUpdater from(int id, String name)
    {
        return new ModelRecord(id, name);
    }

    /**
     * Returns the id of the model.
     *
     * @return The id of the model.
     */
    int getId();

    /**
     * Returns the name of the model.
     *
     * @return The name of the model.
     */
    String getName();

    /**
     * Sets the name of the model.
     *
     * @param name The new name of the model.
     */
    void setName(String name);
}
