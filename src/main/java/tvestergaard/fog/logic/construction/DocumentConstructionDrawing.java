package tvestergaard.fog.logic.construction;

import org.w3c.dom.Document;

public class DocumentConstructionDrawing implements ConstructionDrawing
{

    /**
     * The name of the drawing.
     */
    private final String name;

    /**
     * The document of the svg drawing.
     */
    private final Document document;

    /**
     * Creates a new {@link DocumentConstructionDrawing}.
     *
     * @param name The name of the drawing.
     */
    public DocumentConstructionDrawing(String name, Document document)
    {
        this.name = name;
        this.document = document;
    }

    /**
     * Returns The name of the drawing.
     *
     * @return The name of the drawing.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Returns the svg contents of the drawing.
     *
     * @return The svg contents of the drawing.
     */
    @Override public String getSVG()
    {
        return document.toString();
    }
}
