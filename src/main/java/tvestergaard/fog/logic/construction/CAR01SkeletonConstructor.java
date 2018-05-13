package tvestergaard.fog.logic.construction;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.orders.Shed;

import static org.apache.batik.anim.dom.SVGDOMImplementation.SVG_NAMESPACE_URI;
import static tvestergaard.fog.logic.construction.DrawingUtilities.Rotation.HORIZONTAL;
import static tvestergaard.fog.logic.construction.DrawingUtilities.Rotation.VERTICAL;

public class CAR01SkeletonConstructor extends DrawingUtilities implements SkeletonConstructor
{

    private CAR01Components components;

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

        this.components = new CAR01Components(components);

        Materials           materials  = calculateMaterials(specification);
        ConstructionDrawing aerialView = drawAerialView(specification);
        ConstructionDrawing sideView   = drawSideView(specification);

        return new DefaultSkeletonConstructionSummary(materials, aerialView, sideView);
    }

    /**
     * Constructs the material list of the construction of the garage skeleton.
     *
     * @param specification The specifications that the skeleton must satisfy.
     * @return The resulting material list.
     */
    private Materials calculateMaterials(ConstructionSpecification specification)
    {
        MutableMaterials materials = new MutableMaterials();

        Component garagePosts  = components.getGaragePosts();
        Component garageStraps = components.getGarageStraps();
        Component gableNogging = components.getShedGableNogging();
        Component shedCladding = components.getShedCladding();
        Component doorNogging  = components.getDoorNogging();

        materials.add(garagePosts.getMaterial(), 8, garagePosts.getNotes());
        materials.add(garageStraps.getMaterial(), 2, garageStraps.getNotes());
        materials.add(doorNogging.getMaterial(), 1, doorNogging.getNotes());
        materials.add(gableNogging.getMaterial(), 20, gableNogging.getNotes());
        materials.add(shedCladding.getMaterial(), 12, shedCladding.getNotes());

        return materials;
    }

    /**
     * Draws the areal view of the skeleton.
     *
     * @param specification The specifications of the garage skeleton.
     * @return The resulting drawing.
     */
    private ConstructionDrawing drawAerialView(ConstructionSpecification specification)
    {
        Document document = createDocument(this.outerLength + PADDING * 2, this.outerWidth + PADDING * 2);
        drawAerialPosts(document, specification);
        drawAerialStraps(document, specification);

        return new DocumentConstructionDrawing(document);
    }

    /**
     * Draws the side view of the skeleton.
     *
     * @param specification The specifications of the garage skeleton.
     * @return The resulting drawing.
     */
    private ConstructionDrawing drawSideView(ConstructionSpecification specification)
    {
        Document document = createDocument(this.outerLength + PADDING * 2, this.width + PADDING * 2);
        drawSideStraps(document);
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
        int  currentX  = this.outerLength + PADDING - END_OVERHANG_MM - thickness;
        int  topRow    = PADDING;
        int  shedDepth = mm(shed.getDepth());
        int  height    = mm(specification.getHeight());

        drawPost(document, thickness, height, currentX, topRow);
        currentX -= shedDepth;
        currentX -= thickness;
        drawPost(document, thickness, height, currentX, topRow);

        int firstColumn = PADDING + END_OVERHANG_MM;
        drawPost(document, thickness, height, firstColumn, topRow);
        int secondColumn = firstColumn + (currentX - firstColumn) / 2;
        drawPost(document, thickness, height, secondColumn, topRow);

        ruler(document, VERTICAL, height, PADDING / 2, PADDING, String.format("%d cm", specification.getHeight()));
    }

    /**
     * Draws the straps seen from the side view.
     *
     * @param document The document to draw the straps on.
     */
    private void drawSideStraps(Document document)
    {
        rect(document, this.outerLength, 195, PADDING, PADDING);
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

        rect(document, this.outerLength, thickness, PADDING, PADDING + SIDE_OVERHANG_MM);
        rect(document, this.outerLength, thickness, PADDING, this.width + PADDING + SIDE_OVERHANG_MM - 97 / 2);

        ruler(document, HORIZONTAL, this.outerLength, PADDING, this.outerWidth + PADDING + PADDING / 2, formatCM(outerLength / 10));
        ruler(document, VERTICAL, this.width, PADDING / 2, PADDING + SIDE_OVERHANG_MM, formatCM(specification.getWidth()));
        ruler(document, HORIZONTAL, END_OVERHANG_MM, PADDING, PADDING / 2, formatCM(END_OVERHANG_MM / 10));
        ruler(document, HORIZONTAL, END_OVERHANG_MM, PADDING + outerLength - END_OVERHANG_MM, PADDING / 2, formatCM(END_OVERHANG_MM / 10));
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
        int  currentX  = this.outerLength + PADDING - END_OVERHANG_MM - thickness;
        int  topRow    = PADDING + SIDE_OVERHANG_MM;
        int  bottomRow = topRow + this.width - 97;
        int  shedDepth = mm(shed.getDepth());

        drawPost(document, thickness, thickness, currentX, topRow);
        drawPost(document, thickness, thickness, currentX, bottomRow);
        currentX -= shedDepth;
        currentX -= thickness;
        drawPost(document, thickness, thickness, currentX, topRow);
        drawPost(document, thickness, thickness, currentX, bottomRow);

        ruler(document, HORIZONTAL, shedDepth, currentX + 97, PADDING / 2, formatCM((shedDepth - 97 * 2) / 10));

        int firstColumn = PADDING + END_OVERHANG_MM;
        drawPost(document, thickness, thickness, firstColumn, topRow);
        drawPost(document, thickness, thickness, firstColumn, bottomRow);
        int secondColumn = firstColumn + (currentX - firstColumn) / 2;
        drawPost(document, thickness, thickness, secondColumn, topRow);
        drawPost(document, thickness, thickness, secondColumn, bottomRow);

        ruler(
                document,
                HORIZONTAL,
                secondColumn - firstColumn - 97,
                firstColumn + 97,
                PADDING / 2,
                formatCM((secondColumn - firstColumn) / 10));

        ruler(document, HORIZONTAL,
                (currentX - firstColumn) / 2 - 97, secondColumn + 97,
                PADDING / 2,
                formatCM((currentX - firstColumn) / 2 / 10));
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
