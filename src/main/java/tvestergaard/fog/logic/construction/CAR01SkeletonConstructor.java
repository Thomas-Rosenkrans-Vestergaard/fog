package tvestergaard.fog.logic.construction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.orders.Shed;

import static org.apache.batik.anim.dom.SVGDOMImplementation.SVG_NAMESPACE_URI;

public class CAR01SkeletonConstructor extends DrawingUtilities implements SkeletonConstructor
{

    private static final int SIDE_OVERHANG_MM = 250;
    private static final int END_OVERHANG_MM  = 500;
    private static final int DRAWING_PADDING  = 1000;

    private int length;
    private int outerLength;
    private int width;
    private int outerWidth;
    private int height;

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param specification The specifications that the skeleton must satisfy.
     * @param components    The components to use while constructing the skeleton.
     */
    @Override public SkeletonConstructionSummary construct(ConstructionSpecification specification, Components components)
    {
        this.length = mm(specification.getLength());
        this.outerLength = length + END_OVERHANG_MM * 2;
        this.width = mm(specification.getWidth());
        this.outerWidth = width + SIDE_OVERHANG_MM * 2;
        this.height = mm(specification.getHeight());

        Materials           materials  = calculateMaterials(specification, components);
        ConstructionDrawing aerialView = drawAerialView(specification, components);
        ConstructionDrawing sideView   = drawSideView(specification, components);

        return new DefaultSkeletonConstructionSummary(materials, aerialView, sideView);
    }

    /**
     * Constructs the material list of the construction of the garage skeleton.
     *
     * @param specification The specifications that the skeleton must satisfy.
     * @param components    The components to use while constructing the skeleton.
     * @return The resulting material list.
     */
    private Materials calculateMaterials(ConstructionSpecification specification, Components components)
    {
        MutableMaterials materials = new MutableMaterials();

        Component post         = components.from("POST");
        Component straps       = components.from("STRAPS_GARAGE");
        Component loesHolter   = components.from("LOESHOLTER_SHED_GABLE");
        Component shedCladding = components.from("SHED_CLADDING");
        Component zDoor        = components.from("Z_DOOR");

        materials.add(post.getMaterial(), 8, post.getNotes());
        materials.add(straps.getMaterial(), 2, straps.getNotes());
        materials.add(zDoor.getMaterial(), 1, zDoor.getNotes());
        materials.add(loesHolter.getMaterial(), 20, loesHolter.getNotes());
        materials.add(shedCladding.getMaterial(), 12, shedCladding.getNotes());

        return materials;
    }

    /**
     * Draws the areal view of the skeleton.
     *
     * @param specification The specifications of the garage skeleton.
     * @param components    The components to use when constructing the skeleton.
     * @return The resulting drawing.
     */
    private ConstructionDrawing drawAerialView(ConstructionSpecification specification, Components components)
    {
        Document document = createDocument(
                this.outerLength + DRAWING_PADDING * 2,
                this.outerWidth + DRAWING_PADDING * 2);
        drawAerialPosts(document, specification);
        drawAerialStraps(document, specification);

        return new DocumentConstructionDrawing(document);
    }

    /**
     * Draws the side view of the skeleton.
     *
     * @param specification The specifications of the garage skeleton.
     * @param components    The components to use when constructing the skeleton.
     * @return The resulting drawing.
     */
    private ConstructionDrawing drawSideView(ConstructionSpecification specification, Components components)
    {
        Document document = createDocument(
                this.outerLength + DRAWING_PADDING * 2,
                this.outerWidth + DRAWING_PADDING * 2);
        drawSideStraps(document, specification);
        drawSidePosts(document, specification);

        return new DocumentConstructionDrawing(document);
    }

    /**
     * Draws the posts seen from the side view.
     *
     * @param document      The document to draw the straps on.
     * @param specification The specifications of the garage skeleton.
     */
    private void drawSidePosts(Document document, ConstructionSpecification specification)
    {
        Shed shed      = specification.getShed();
        int  thickness = 97;
        int  currentX  = this.outerLength + DRAWING_PADDING - END_OVERHANG_MM - thickness;
        int  topRow    = DRAWING_PADDING;
        int  shedDepth = mm(shed.getDepth());
        int  height    = mm(specification.getHeight());

        // Shed posts
        drawPost(document, thickness, height, currentX, topRow);
        currentX -= shedDepth;
        currentX -= thickness;
        drawPost(document, thickness, height, currentX, topRow);

        // Overhang posts
        int firstColumn = DRAWING_PADDING + END_OVERHANG_MM;
        drawPost(document, thickness, height, firstColumn, topRow);
        int secondColumn = firstColumn + (currentX - firstColumn) / 2;
        drawPost(document, thickness, height, secondColumn, topRow);

        ruler(document, Rotation.VERTICAL, height, DRAWING_PADDING / 2, DRAWING_PADDING, String.format("%d cm", specification.getHeight()));
    }

    /**
     * Draws the straps seen from the side view.
     *
     * @param document      The document to draw the straps on.
     * @param specification The specifications of the garage skeleton.
     */
    private void drawSideStraps(Document document, ConstructionSpecification specification)
    {
        rect(document, this.outerLength, 195, DRAWING_PADDING, DRAWING_PADDING);
    }

    /**
     * Draws the straps seen from the aerial view.
     *
     * @param document      The document to draw the straps on.
     * @param specification The specifications of the garage skeleton.
     */
    private void drawAerialStraps(Document document, ConstructionSpecification specification)
    {
        final int thickness = 45;

        rect(document, this.outerLength, thickness, DRAWING_PADDING, DRAWING_PADDING + SIDE_OVERHANG_MM);
        rect(document, this.outerLength, thickness, DRAWING_PADDING, this.width + DRAWING_PADDING + SIDE_OVERHANG_MM - 97 / 2);

        ruler(document, Rotation.HORIZONTAL, this.outerLength, DRAWING_PADDING, this.outerWidth + DRAWING_PADDING + DRAWING_PADDING / 2,
                String.format("%d cm", outerLength / 10));
        ruler(document, Rotation.VERTICAL, this.width, DRAWING_PADDING / 2, DRAWING_PADDING + SIDE_OVERHANG_MM,
                String.format("%d cm", specification.getWidth()));

        ruler(document, Rotation.HORIZONTAL, END_OVERHANG_MM, DRAWING_PADDING, DRAWING_PADDING + SIDE_OVERHANG_MM + 400,
                String.format("%d cm", END_OVERHANG_MM / 10));

        ruler(document, Rotation.HORIZONTAL, END_OVERHANG_MM, DRAWING_PADDING + outerLength - END_OVERHANG_MM, DRAWING_PADDING + SIDE_OVERHANG_MM + 400,
                String.format("%d cm", END_OVERHANG_MM / 10));
    }

    /**
     * Draws the posts seen from the aerial view.
     *
     * @param document      The document to draw the straps on.
     * @param specification The specifications of the garage skeleton.
     */
    private void drawAerialPosts(Document document, ConstructionSpecification specification)
    {
        Shed shed      = specification.getShed();
        int  thickness = 97;
        int  currentX  = this.outerLength + DRAWING_PADDING - END_OVERHANG_MM - thickness;
        int  topRow    = DRAWING_PADDING + SIDE_OVERHANG_MM;
        int  bottomRow = topRow + this.width - 97;
        int  shedDepth = mm(shed.getDepth());

        // Shed posts
        drawPost(document, thickness, thickness, currentX, topRow);
        drawPost(document, thickness, thickness, currentX, bottomRow);
        currentX -= shedDepth;
        currentX -= thickness;
        drawPost(document, thickness, thickness, currentX, topRow);
        drawPost(document, thickness, thickness, currentX, bottomRow);

        ruler(
                document,
                Rotation.HORIZONTAL,
                shedDepth,
                currentX + 97,
                DRAWING_PADDING + SIDE_OVERHANG_MM + 400,
                String.format("%s cm", (shedDepth - 97 * 2) / 10));

        // Overhang posts
        int firstColumn = DRAWING_PADDING + END_OVERHANG_MM;
        drawPost(document, thickness, thickness, firstColumn, topRow);
        drawPost(document, thickness, thickness, firstColumn, bottomRow);
        int secondColumn = firstColumn + (currentX - firstColumn) / 2;
        drawPost(document, thickness, thickness, secondColumn, topRow);
        drawPost(document, thickness, thickness, secondColumn, bottomRow);

        ruler(
                document,
                Rotation.HORIZONTAL,
                secondColumn - firstColumn - 97,
                firstColumn + 97,
                DRAWING_PADDING + SIDE_OVERHANG_MM + 400,
                String.format("%s cm", (secondColumn - firstColumn) / 10));

        ruler(document, Rotation.HORIZONTAL,
                (currentX - firstColumn) / 2 - 97, secondColumn + 97,
                DRAWING_PADDING + SIDE_OVERHANG_MM + 400,
                String.format("%d cm", ((currentX - firstColumn) / 2) / 10));
    }

    /**
     * Draws a post.
     *
     * @param document
     * @param width
     * @param height
     * @param currentX
     * @param currentY
     */
    private void drawPost(Document document, int width, int height, int currentX, int currentY)
    {
        Element root = document.getDocumentElement();

        Element rectangle = document.createElementNS(SVG_NAMESPACE_URI, "rect");
        rectangle.setAttributeNS(null, "width", Integer.toString(width));
        rectangle.setAttributeNS(null, "height", Integer.toString(height));
        rectangle.setAttributeNS(null, "style", "stroke:black");
        rectangle.setAttributeNS(null, "x", Integer.toString(currentX));
        rectangle.setAttributeNS(null, "y", Integer.toString(currentY));
        root.appendChild(rectangle);
    }
}
