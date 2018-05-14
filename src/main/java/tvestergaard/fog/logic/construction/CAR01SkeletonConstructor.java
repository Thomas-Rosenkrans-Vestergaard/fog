package tvestergaard.fog.logic.construction;

import org.w3c.dom.Document;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.orders.Shed;

import static tvestergaard.fog.logic.construction.DrawingUtilities.Rotation.HORIZONTAL;
import static tvestergaard.fog.logic.construction.DrawingUtilities.Rotation.VERTICAL;

public class CAR01SkeletonConstructor extends DrawingUtilities implements SkeletonConstructor
{

    protected static final int SIDE_OVERHANG_MM = 250;
    protected static final int END_OVERHANG_MM  = 500;
    protected static final int PADDING          = 1000;

    protected int             length;
    protected int             outerLength;
    protected int             width;
    protected int             outerWidth;
    private   CAR01Components components;

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

        Materials materials      = calculateMaterials(specification);
        Document  aerialDocument = createDocument(this.outerLength + PADDING * 2, this.outerWidth + PADDING * 2);
        Document  sideDocument   = createDocument(this.outerLength + PADDING * 2, mm(specification.getHeight()) + PADDING * 2);

        drawStraps(aerialDocument, sideDocument, specification);
        drawPosts(aerialDocument, sideDocument, specification);

        return new DefaultSkeletonConstructionSummary(
                materials,
                new DocumentConstructionDrawing(aerialDocument),
                new DocumentConstructionDrawing(sideDocument));
    }


    private void drawStraps(Document aerialDocument,
                            Document sideDocument,
                            ConstructionSpecification specification)
    {
        final int thickness = 45;

        rect(sideDocument, this.outerLength, 195, PADDING, PADDING);
        rect(aerialDocument, this.outerLength, thickness, PADDING, PADDING + SIDE_OVERHANG_MM);
        rect(aerialDocument, this.outerLength, thickness, PADDING, this.width + PADDING + SIDE_OVERHANG_MM - 97 / 2);

        ruler(aerialDocument, HORIZONTAL, this.outerLength, PADDING, this.outerWidth + PADDING + PADDING / 2, formatCM(outerLength / 10));
        ruler(aerialDocument, VERTICAL, this.width, PADDING / 2, PADDING + SIDE_OVERHANG_MM, formatCM(specification.getWidth()));
        ruler(aerialDocument, HORIZONTAL, END_OVERHANG_MM, PADDING, PADDING / 2, formatCM(END_OVERHANG_MM / 10));
        ruler(aerialDocument, HORIZONTAL, END_OVERHANG_MM, PADDING + outerLength - END_OVERHANG_MM, PADDING / 2, formatCM(END_OVERHANG_MM / 10));
    }

    private void drawPosts(Document aerialDocument,
                           Document sideDocument,
                           ConstructionSpecification specification)
    {
        Component post      = components.getGaragePosts();
        Shed      shed      = specification.getShed();
        int       thickness = post.getMaterial().getAttribute("THICKNESS_MM").getInt();
        int       x         = this.outerLength + PADDING - END_OVERHANG_MM - thickness;
        int       topRow    = PADDING + SIDE_OVERHANG_MM;
        int       bottomRow = topRow + this.width - thickness;
        int       shedDepth = mm(shed.getDepth());
        int       height    = mm(specification.getHeight());

        filledRect(sideDocument, thickness, height, x, PADDING);
        filledRect(aerialDocument, thickness, thickness, x, topRow);
        filledRect(aerialDocument, thickness, thickness, x, bottomRow);

        x -= shedDepth;
        x -= thickness;

        filledRect(sideDocument, thickness, height, x, PADDING);
        filledRect(aerialDocument, thickness, thickness, x, topRow);
        filledRect(aerialDocument, thickness, thickness, x, bottomRow);

        int firstColumn  = PADDING + END_OVERHANG_MM;
        int secondColumn = firstColumn + (x - firstColumn) / 2;

        filledRect(sideDocument, thickness, height, firstColumn, PADDING);
        filledRect(aerialDocument, thickness, thickness, firstColumn, topRow);
        filledRect(aerialDocument, thickness, thickness, firstColumn, bottomRow);

        filledRect(sideDocument, thickness, height, secondColumn, PADDING);
        filledRect(aerialDocument, thickness, thickness, secondColumn, topRow);
        filledRect(aerialDocument, thickness, thickness, secondColumn, bottomRow);

        ruler(aerialDocument, HORIZONTAL, shedDepth, x + 97, PADDING / 2, formatCM((shedDepth - 97 * 2) / 10));

        ruler(
                aerialDocument,
                HORIZONTAL,
                secondColumn - firstColumn - 97,
                firstColumn + 97,
                PADDING / 2,
                formatCM((secondColumn - firstColumn) / 10));

        ruler(aerialDocument, HORIZONTAL,
                (x - firstColumn) / 2 - 97, secondColumn + 97,
                PADDING / 2,
                formatCM((x - firstColumn) / 2 / 10));
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

        filledRect(document, thickness, thickness, currentX, topRow);
        filledRect(document, thickness, thickness, currentX, bottomRow);
        currentX -= shedDepth;
        currentX -= thickness;
        filledRect(document, thickness, thickness, currentX, topRow);
        filledRect(document, thickness, thickness, currentX, bottomRow);

        ruler(document, HORIZONTAL, shedDepth, currentX + 97, PADDING / 2, formatCM((shedDepth - 97 * 2) / 10));

        int firstColumn = PADDING + END_OVERHANG_MM;
        filledRect(document, thickness, thickness, firstColumn, topRow);
        filledRect(document, thickness, thickness, firstColumn, bottomRow);
        int secondColumn = firstColumn + (currentX - firstColumn) / 2;
        filledRect(document, thickness, thickness, secondColumn, topRow);
        filledRect(document, thickness, thickness, secondColumn, bottomRow);

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
}
