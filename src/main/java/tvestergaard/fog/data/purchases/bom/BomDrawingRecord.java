package tvestergaard.fog.data.purchases.bom;

import java.util.Objects;

public class BomDrawingRecord implements BomDrawing
{

    /**
     * The id of the drawing.
     */
    private final int id;

    /**
     * The title of the drawing.
     */
    private final String title;

    /**
     * The svg contents of the drawing.
     */
    private final String contents;

    /**
     * Creates a new {@link BomDrawingRecord}.
     *
     * @param id       The id of the drawing.
     * @param title    The title of the drawing.
     * @param contents The svg contents of the drawing.
     */
    public BomDrawingRecord(int id, String title, String contents)
    {
        this.id = id;
        this.title = title;
        this.contents = contents;
    }

    /**
     * Returns the id of the drawing.
     *
     * @return The id of the drawing.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the title of the drawing.
     *
     * @return The title of the drawing.
     */
    @Override public String getTitle()
    {
        return title;
    }

    /**
     * Returns the svg contents of the drawing.
     *
     * @return The svg contents of the drawing.
     */
    @Override public String getContent()
    {
        return contents;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BomDrawingRecord)) return false;
        BomDrawingRecord that = (BomDrawingRecord) o;
        return getId() == that.getId() &&
                Objects.equals(getTitle(), that.getTitle()) &&
                Objects.equals(getContent(), that.getContent());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId(), getTitle(), getContent());
    }

    @Override public String toString()
    {
        return "DrawingRecord{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
