package tvestergaard.fog.logic.construction;

import org.w3c.dom.Document;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.orders.Shed;

import static tvestergaard.fog.logic.construction.DrawingUtilities.Rotation.HORIZONTAL;
import static tvestergaard.fog.logic.construction.DrawingUtilities.Rotation.VERTICAL;

public class CarportSkeletonConstructor extends DrawingUtilities implements SkeletonConstructor
{

    /**
     * The amount of overhang on the sides of the garage (in millimeters).
     */
    private static final int SIDE_OVERHANG_MM = 250;

    /**
     * The amount of overhang on the ends of the garage (in millimeters).
     */
    private static final int END_OVERHANG_MM = 500;

    /**
     * The amount of padding on the sides of the drawing.
     */
    private static final int PADDING = 1000;

    private int                       length;
    private int                       outerLength;
    private int                       width;
    private int                       outerWidth;
    private int                       height;
    private MutableMaterials          materials;
    private Document                  aerialDocument;
    private Document                  sideDocument;
    private CAR01Components           components;
    private ConstructionSpecification specification;

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

        this.components = new CAR01Components(components);
        this.specification = specification;
        materials = new MutableMaterials();
        aerialDocument = createDocument(this.outerLength + PADDING * 2, this.outerWidth + PADDING * 2);
        sideDocument = createDocument(this.outerLength + PADDING * 2, height + PADDING * 2);

        straps(specification);
        posts(specification);
        calculateMaterials(specification);

        return new DefaultSkeletonConstructionSummary(
                materials,
                new DocumentConstructionDrawing(aerialDocument),
                new DocumentConstructionDrawing(sideDocument));
    }


    /**
     * Draws and adds the straps to the material list.
     *
     * @param specification The specifications of the garage to construct.
     */
    private void straps(ConstructionSpecification specification)
    {
        Component component = components.getGarageStraps();
        Material  material  = component.getMaterial();

        materials.add(material, 2, component.getNotes());

        final int thickness = material.getAttribute("THICKNESS_MM").getInt();
        final int width     = 195;// material.getAttribute("WIDTH_MM").getInt();

        rect(sideDocument, this.outerLength, width, PADDING, PADDING);
        rect(aerialDocument, this.outerLength, thickness, PADDING, PADDING + SIDE_OVERHANG_MM);
        rect(aerialDocument, this.outerLength, thickness, PADDING, this.width + PADDING + SIDE_OVERHANG_MM - 97 / 2);

        ruler(HORIZONTAL, this.outerLength, PADDING, this.outerWidth + PADDING + PADDING / 2, formatCM(outerLength / 10));
        ruler(HORIZONTAL, END_OVERHANG_MM, PADDING, PADDING / 2, formatCM(END_OVERHANG_MM / 10));
        ruler(HORIZONTAL, END_OVERHANG_MM, PADDING + outerLength - END_OVERHANG_MM, PADDING / 2, formatCM(END_OVERHANG_MM / 10));

        ruler(aerialDocument, VERTICAL, this.width, PADDING / 2, PADDING + SIDE_OVERHANG_MM, formatCM(specification.getWidth()));
    }

    /**
     * Draws and adds the garage posts to the material list.
     *
     * @param specification The specification
     */
    private void posts(ConstructionSpecification specification)
    {
        Component component       = components.getGaragePosts();
        Shed      shed            = specification.getShed();
        int       thickness       = component.getMaterial().getAttribute("THICKNESS_MM").getInt();
        int       x               = this.outerLength + PADDING - END_OVERHANG_MM - thickness;
        int       topRow          = PADDING + SIDE_OVERHANG_MM;
        int       bottomRow       = topRow + this.width - thickness;
        int       height          = mm(specification.getHeight());
        int       numberOfColumns = Math.max(2, 1 + specification.getLength() / 200);
        int       shedDepth       = mm(shed.getDepth());

        materials.add(component.getMaterial(), numberOfColumns * 2, component.getNotes());

        numberOfColumns -= 1;
        placePostColumn(thickness, height, x, topRow, bottomRow);

        if (shed != null) {
            x -= shedDepth;
            x -= thickness;
            numberOfColumns -= 1;
            placePostColumn(thickness, height, x, topRow, bottomRow);
            ruler(HORIZONTAL, shedDepth, x + thickness, PADDING / 2, formatCM(shedDepth / 10));
            shed(shed, x);
        }

        int columnDistance = (x - END_OVERHANG_MM - PADDING) / numberOfColumns;
        for (int column = numberOfColumns; column >= 1; column--) {
            x -= columnDistance;
            placePostColumn(thickness, height, x, topRow, bottomRow);
            ruler(HORIZONTAL, columnDistance - thickness, x + thickness, PADDING / 2,
                    formatCM((columnDistance - thickness) / 10));
        }

        ruler(sideDocument, VERTICAL, height, PADDING / 2, PADDING, formatCM(specification.getHeight()));
    }

    private void shed(Shed shed, int x)
    {
        Component componentCladding  = components.getShedCladding();
        Material  materialCladding   = componentCladding.getMaterial();
        int       claddingThickness  = materialCladding.getAttribute("THICKNESS_MM").getInt();
        int       claddingWidth      = materialCladding.getAttribute("WIDTH_MM").getInt();
        int       numberOfEndBoards  = width / claddingWidth;
        int       shedDepth          = mm(shed.getDepth());
        int       numberOfSideBoards = (shedDepth - 97 * 2) / claddingWidth;

        int thickness = 97;

        for (int endBoard = 1; endBoard < numberOfEndBoards - 1; endBoard++) {
            int leftColumn  = x;
            int rightColumn = x + shedDepth + 97 * 2 - claddingThickness;
            int y           = PADDING + SIDE_OVERHANG_MM + thickness + ((endBoard - 1) * claddingWidth);
            rect(aerialDocument, claddingThickness, claddingWidth, leftColumn, y);
            rect(aerialDocument, claddingThickness, claddingWidth, leftColumn + claddingThickness, y + claddingWidth / 2);
            rect(aerialDocument, claddingThickness, claddingWidth, rightColumn, y);
            rect(aerialDocument, claddingThickness, claddingWidth, rightColumn - claddingThickness, y - claddingWidth / 2);
        }
    }

    /**
     * Places a ruler using the provided specifications on both the aerial and side view.
     *
     * @param rotation The rotation of the ruler.
     * @param length   The length of the ruler.
     * @param x        The x position of the start of the ruler.
     * @param y        The y position of the start of the ruler.
     * @param text     The text shown on the ruler.
     */
    private void ruler(Rotation rotation, int length, int x, int y, String text)
    {
        ruler(aerialDocument, rotation, length, x, y, text);
        ruler(sideDocument, rotation, length, x, y, text);
    }

    /**
     * Places a column of posts.
     *
     * @param thickness The thickness of the post.
     * @param height    The height of the post.
     * @param x         The x position the post is placed at (left side of post).
     * @param topRow    The y position of the top row.
     * @param bottomRow The y position of the bottom row.
     */
    private void placePostColumn(int thickness, int height, int x, int topRow, int bottomRow)
    {
        filledRect(sideDocument, thickness, height, x, PADDING);
        filledRect(aerialDocument, thickness, thickness, x, topRow);
        filledRect(aerialDocument, thickness, thickness, x, bottomRow);
    }

    /**
     * Constructs the material list of the construction of the garage skeleton.
     *
     * @param specification The specifications that the skeleton must satisfy.
     * @return The resulting material list.
     */
    private Materials calculateMaterials(ConstructionSpecification specification)
    {
        Component gableNogging = components.getShedGableNogging();
        Component shedCladding = components.getShedCladding();
        Component doorNogging  = components.getDoorNogging();

        materials.add(doorNogging.getMaterial(), 1, doorNogging.getNotes());
        materials.add(gableNogging.getMaterial(), 20, gableNogging.getNotes());
        materials.add(shedCladding.getMaterial(), 12, shedCladding.getNotes());

        return materials;
    }
}
