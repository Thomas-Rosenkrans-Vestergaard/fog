package tvestergaard.fog.data.materials.categories;

import java.util.Objects;

public class CategoryRecord implements Category
{

    /**
     * The id of the category.
     */
    private final int id;

    /**
     * The name of the category.
     */
    private final String name;

    /**
     * Creates a new {@link CategoryRecord}.
     *
     * @param id   The id of the category.
     * @param name The name of the category.
     */
    public CategoryRecord(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    /**
     * Returns the id of the category.
     *
     * @return The id of the category.
     */
    public int getId()
    {
        return this.id;
    }

    /**
     * Returns the name of the category.
     *
     * @return The name of the category.
     */
    public String getName()
    {
        return this.name;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category that = (Category) o;
        return getId() == that.getId() &&
                Objects.equals(getName(), that.getName());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }

    @Override public String toString()
    {
        return "CategoryRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
