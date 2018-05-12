package tvestergaard.fog.logic.construction;

import org.w3c.dom.Document;

public interface ConstructionDrawing
{

    /**
     * Returns the document containing the svg contents.
     *
     * @return The document containing the svg contents.
     */
    Document getDocument();

    /**
     * Returns the SVG XML contents.
     *
     * @return The SVG XML contents.
     */
    String getXML();
}
