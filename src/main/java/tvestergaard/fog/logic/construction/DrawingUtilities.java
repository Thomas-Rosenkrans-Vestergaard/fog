package tvestergaard.fog.logic.construction;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static org.apache.batik.util.SVGConstants.SVG_NAMESPACE_URI;

public class DrawingUtilities
{

    protected static final int SIDE_OVERHANG_MM = 250;
    protected static final int END_OVERHANG_MM  = 500;
    protected static final int PADDING          = 1000;

    protected int length;
    protected int outerLength;
    protected int width;
    protected int outerWidth;

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

    public Document createDocument(int viewBoxWidth, int viewBoxHeight)
    {
        DOMImplementation impl     = SVGDOMImplementation.getDOMImplementation();
        String            svgNS    = SVG_NAMESPACE_URI;
        Document          document = impl.createDocument(svgNS, "svg", null);

        Element root = document.getDocumentElement();
        root.setAttributeNS(null, "viewBox", String.format("0 0 %s %s", viewBoxWidth, viewBoxHeight));
        return document;
    }

    public void copy(Document source, Document destination)
    {
        destination.replaceChild(destination.importNode(source.getDocumentElement(), true), destination.getDocumentElement());
    }

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

    public void filledRect(Document document, int width, int height, int x, int y)
    {
        Element root      = document.getDocumentElement();
        Element rectangle = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
        rectangle.setAttributeNS(null, "width", Integer.toString(width));
        rectangle.setAttributeNS(null, "height", Integer.toString(height));
        rectangle.setAttributeNS(null, "style", "fill:black;stroke-width:5;stroke:black");
        rectangle.setAttributeNS(null, "x", Integer.toString(x));
        rectangle.setAttributeNS(null, "y", Integer.toString(y));
        root.appendChild(rectangle);
    }

    public void ruler(Document document, Rotation rotation, int length, int x, int y, String text)
    {
        int height = 50;

        if (rotation == Rotation.HORIZONTAL) {
            line(document, x, y, x + length, y);
            line(document, x, y - height, x, y + height);
            line(document, x + length, y - height, x + length, y + height);
            text(document, x + length / 2, y - 100, text);
        } else {
            line(document, x, y, x, y + length);
            line(document, x - height, y, x + height, y);
            line(document, x - height, y + length, x + height, y + length);
            text(document, x + 100, y + length / 2, text);
        }
    }

    private void text(Document document, int x, int y, String text)
    {
        Element root     = document.getDocumentElement();
        Element textNode = document.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
        textNode.setAttributeNS(null, "x", Integer.toString(x));
        textNode.setAttributeNS(null, "y", Integer.toString(y));
        textNode.setAttributeNS(null, "style", "font-family:Arial;font-size:130;stroke:#000000;#fill:#00ff00;");
        textNode.setTextContent(text);
        root.appendChild(textNode);
    }

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
