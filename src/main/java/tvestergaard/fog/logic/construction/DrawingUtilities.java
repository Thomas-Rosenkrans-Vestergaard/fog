package tvestergaard.fog.logic.construction;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.apache.batik.util.SVGConstants.SVG_NAMESPACE_URI;

public class DrawingUtilities
{

    public enum Rotation
    {
        HORIZONTAL,
        VERTICAL;

        public Rotation other()
        {
            if (this == HORIZONTAL)
                return VERTICAL;
            else
                return HORIZONTAL;
        }
    }

    /**
     * Creates a new document with the provided viewbox dimensions.
     *
     * @param viewBoxWidth  The width of the viewbox of the document to create.
     * @param viewBoxHeight The height of the viewbox of the document to create.
     * @return The newly created document.
     */
    public Document createDocument(int viewBoxWidth, int viewBoxHeight)
    {
        DOMImplementation impl     = SVGDOMImplementation.getDOMImplementation();
        String            svgNS    = SVG_NAMESPACE_URI;
        Document          document = impl.createDocument(svgNS, "svg", null);

        Element root = document.getDocumentElement();
        root.setAttributeNS(null, "viewBox", String.format("0 0 %s %s", viewBoxWidth, viewBoxHeight));
        return document;
    }

    /**
     * Copies the source document to the destination document.
     *
     * @param source      The source document.
     * @param destination The document the source is copied to.
     */
    public void copy(Document source, Document destination)
    {
        destination.replaceChild(destination.importNode(source.getDocumentElement(), true), destination.getDocumentElement());
    }

    /**
     * Creates a new rectangle in the provided document.
     *
     * @param document The document to draw the rectangle in.
     * @param width    The width of the rectangle.
     * @param height   The height of the rectangle.
     * @param x        The x position of the top left corner of the rectangle.
     * @param y        The y position of the top left corner of the rectangle.
     */
    public void rect(Document document, int width, int height, int x, int y)
    {
        Element root      = document.getDocumentElement();
        Element rectangle = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
        rectangle.setAttributeNS(null, "width", Integer.toString(width));
        rectangle.setAttributeNS(null, "height", Integer.toString(height));
        rectangle.setAttributeNS(null, "style", "fill:white;stroke-width:5;stroke:black");
        rectangle.setAttributeNS(null, "x", Integer.toString(x));
        rectangle.setAttributeNS(null, "y", Integer.toString(y));
        root.appendChild(rectangle);
    }

    /**
     * Creates a new filled black rectangle in the provided document.
     *
     * @param document The document to draw the filled rectangle in.
     * @param width    The width of the filled rectangle.
     * @param height   The height of the filled rectangle.
     * @param x        The x position of the top left corner of the filled rectangle.
     * @param y        The y position of the top left corner of the filled rectangle.
     */
    public void filledRect(Document document, int width, int height, int x, int y)
    {
        Element root      = document.getDocumentElement();
        Element rectangle = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
        rectangle.setAttributeNS(null, "width", Integer.toString(width));
        rectangle.setAttributeNS(null, "height", Integer.toString(height));
        rectangle.setAttributeNS(null, "style", "stroke:black;fill:black");
        rectangle.setAttributeNS(null, "x", Integer.toString(x));
        rectangle.setAttributeNS(null, "y", Integer.toString(y));
        root.appendChild(rectangle);
    }

    /**
     * Creates a new ruler in the provided document.
     *
     * @param document The document to insert the ruler into.
     * @param rotation The rotation of the ruler.
     * @param length   The length of the ruler.
     * @param x        The x position of the start of the ruler. In horizontal rulers the start of the ruler is the left
     *                 most point. In vertical rulers the start of the ruler is the top most point.
     * @param y        The y position of the start of the ruler. In horizontal rulers the start of the ruler is the left most
     *                 point. In vertical rulers the start of the ruler is the top most point.
     * @param text     The text to add to the ruler.
     */
    public void ruler(Document document, Rotation rotation, int length, int x, int y, String text)
    {
        int height = 50;

        if (rotation == Rotation.HORIZONTAL) {
            line(document, x, y, x + length, y);
            line(document, x, y - height, x, y + height);
            line(document, x + length, y - height, x + length, y + height);
            text(document, rotation, x + length / 2, y - 100, text);
        } else {
            line(document, x, y, x, y + length);
            line(document, x - height, y, x + height, y);
            line(document, x - height, y + length, x + height, y + length);
            text(document, rotation, x + 100, y + length / 2, text);
        }
    }

    /**
     * Inserts a middle anchored text node into the provided document.
     *
     * @param document The document to insert the text node into.
     * @param rotation The rotation of the text node.
     * @param x        The x position to insert the text node at.
     * @param y        The y position to insert the text node at.
     * @param text     The text contents.
     */
    private void text(Document document, Rotation rotation, int x, int y, String text)
    {
        Element root     = document.getDocumentElement();
        Element textNode = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
        textNode.setAttributeNS(null, "x", Integer.toString(x));
        textNode.setAttributeNS(null, "y", Integer.toString(y));
        textNode.setAttributeNS(null, "text-anchor", "middle");
        if (rotation == Rotation.VERTICAL) {
            textNode.setAttributeNS(null, "x", Integer.toString(x - 100));
        }
        textNode.setAttributeNS(null, "style", "font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;");
        textNode.setTextContent(text);
        root.appendChild(textNode);
    }

    /**
     * Inserts a line into the provided document.
     *
     * @param document The document to insert the line into.
     * @param x1       The x position of the start of the line.
     * @param y1       The y position of the start of the line.
     * @param x2       The x position of the end of the line.
     * @param y2       The y position of the end of the line.
     */
    public void line(Document document, int x1, int y1, int x2, int y2)
    {
        Element root = document.getDocumentElement();
        Element line = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "line");
        line.setAttributeNS(null, "x1", Integer.toString(x1));
        line.setAttributeNS(null, "y1", Integer.toString(y1));
        line.setAttributeNS(null, "x2", Integer.toString(x2));
        line.setAttributeNS(null, "y2", Integer.toString(y2));
        line.setAttributeNS(null, "style", "stroke-width:5;stroke:black");
        root.appendChild(line);
    }

    /**
     * Converts the provided length in centimeters to millimeters.
     *
     * @param cm The length in centimeters.
     * @return The length converted to millimeters.
     */
    public int mm(int cm)
    {
        return cm * 10;
    }

    public String formatCM(int cm)
    {
        return String.format("%d cm", cm);
    }
}
