package tvestergaard.fog.data.materials;

import java.util.Objects;

public class MaterialRecord implements Material
{

    /**
     * The unique identifier of the material.
     */
    private final int id;

    /**
     * The material number.
     */
    private final String number;

    /**
     * The description of the material.
     */
    private String description;

    /**
     * Some additional information about the material.
     */
    private String notes;

    /**
     * The height of the material.
     */
    private int width;

    /**
     * The width of the material.
     */
    private int height;

    /**
     * The identifier of the usage of the material when constructing some structure.
     */
    private final int usage;

    /**
     * Creates a new {@link MaterialRecord}.
     *
     * @param id          The unique identifier of the material.
     * @param number      The material number.
     * @param description The description of the material.
     * @param notes       Some additional information about the material.
     * @param width       The height of the material.
     * @param height      The width of the material.
     * @param usage       The identifier of the usage of the material when constructing some structure.
     */
    public MaterialRecord(int id, String number, String description, String notes, int width, int height, int usage)
    {
        this.id = id;
        this.number = number;
        this.description = description;
        this.notes = notes;
        this.width = width;
        this.height = height;
        this.usage = usage;
    }

    /**
     * Returns the unique identifier of the material.
     *
     * @return The unique identifier of the material.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the material number.
     *
     * @return The material number.
     */
    @Override public String getNumber()
    {
        return number;
    }

    /**
     * Returns the description of the material.
     *
     * @return The description of the material.
     */
    @Override public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description of the material.
     *
     * @param description The new description.
     */
    @Override public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Returns some additional information about the material.
     *
     * @return Some additional information about the material.
     */
    @Override public String getNotes()
    {
        return notes;
    }

    /**
     * Sets the additional information about the usage of the material.
     *
     * @param notes The new notes about the usage of the material.
     */
    @Override public void setNotes(String notes)
    {
        this.notes = notes;
    }

    /**
     * Returns the width of the material.
     *
     * @return The width of the material.
     */
    @Override public int getWidth()
    {
        return width;
    }

    /**
     * Sets the width of the material.
     *
     * @param width The new width.
     */
    @Override public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * Returns the height of the material.
     *
     * @return The height of the material.
     */
    @Override public int getHeight()
    {
        return height;
    }

    /**
     * Sets the height of the material.
     *
     * @param height The new height.
     */
    @Override public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * Returns the identifier of the usage of the material when constructing some structure.
     *
     * @return The identifier of the usage of the material when constructing some structure.
     */
    @Override public int getUsage()
    {
        return usage;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Material)) return false;
        Material that = (Material) o;
        return getId() == that.getId() &&
                getHeight() == that.getHeight() &&
                getWidth() == that.getWidth() &&
                getUsage() == that.getUsage() &&
                Objects.equals(getNumber(), that.getNumber()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getNotes(), that.getNotes());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }
}
