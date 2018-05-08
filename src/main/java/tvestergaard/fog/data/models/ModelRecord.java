package tvestergaard.fog.data.models;

import java.util.Objects;

public class ModelRecord implements Model
{

    private final int    id;
    private       String name;

    public ModelRecord(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the id of the model.
     *
     * @return The id of the model.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the model.
     *
     * @return The name of the model.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the model.
     *
     * @param name The new name of the model.
     */
    @Override public void setName(String name)
    {
        this.name = name;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;
        Model that = (Model) o;
        return getId() == that.getId() &&
                Objects.equals(getName(), that.getName());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }

    @Override public String toString()
    {
        return "ModelRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
