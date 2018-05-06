package tvestergaard.fog.data.materials.categories;

import java.util.Objects;

public class CategoryRecord implements Category
{

    private final int    id;
    private final String name;

    public CategoryRecord(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return this.id;
    }

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
}
