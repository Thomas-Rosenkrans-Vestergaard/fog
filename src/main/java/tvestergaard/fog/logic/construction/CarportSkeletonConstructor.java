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

    /**
     * The posts used to support the garage.
     */
    private Component post;

    /**
     * The straps that supports the roof.
     */
    private Component strap;

    /**
     * The wood used to clad the shed.
     */
    private Component shedCladding;

    /**
     * The wood used to support the cladding used on the shed sides.
     */
    private Component shedSideCladdingNogging;

    /**
     * The wood used to support the cladding used on the shed gable.
     */
    private Component shedGableCladdingNogging;

    /**
     * The wood used to support the cladding used to construct the door to the shed.
     */
    private Component shedDoorNogging;

    private int              length;
    private int              outerLength;
    private int              width;
    private int              outerWidth;
    private int              height;
    private MutableMaterials materials;
    private Document         aerialDocument;
    private Document         sideDocument;

    /**
     * Constructs the skeleton of the garage using the provided components.
     *
     * @param specification The specifications of the garage skeleton to construct.
     */
    @Override public SkeletonConstructionSummary construct(ConstructionSpecification specification, ComponentMap components)
    {
        this.length = mm(specification.getLength());
        this.outerLength = length + END_OVERHANG_MM * 2;
        this.width = mm(specification.getWidth());
        this.outerWidth = width + SIDE_OVERHANG_MM * 2;
        this.height = mm(specification.getHeight());

        this.post = components.from("POST");
        this.strap = components.from("STRAPS_GARAGE");
        this.shedSideCladdingNogging = components.from("SHED_SIDE_CLADDING_NOGGING");
        this.shedGableCladdingNogging = components.from("SHED_GABLE_CLADDING_NOGGING");
        this.shedCladding = components.from("SHED_CLADDING");
        this.shedDoorNogging = components.from("SHED_DOOR_NOGGING");

        materials = new MutableMaterials();
        aerialDocument = createDocument(this.outerLength + PADDING * 2, this.outerWidth + PADDING * 2);
        sideDocument = createDocument(this.outerLength + PADDING * 2, height + 195 + PADDING * 2);

        straps(specification);
        posts(specification);

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
        Material material = strap.getMaterial();

        materials.add(material, 2, strap.getNotes());

        int thickness = material.getAttribute("THICKNESS_MM").getInt();
        int width     = material.getAttribute("WIDTH_MM").getInt();

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
        Shed shed            = specification.getShed();
        int  thickness       = post.getMaterial().getAttribute("THICKNESS_MM").getInt();
        int  x               = this.outerLength + PADDING - END_OVERHANG_MM - thickness;
        int  topRow          = PADDING + SIDE_OVERHANG_MM;
        int  bottomRow       = topRow + this.width - thickness;
        int  height          = mm(specification.getHeight());
        int  numberOfColumns = Math.max(2, 1 + specification.getLength() / 200);

        materials.add(post.getMaterial(), numberOfColumns * 2, post.getNotes());

        numberOfColumns -= 1;

        if (shed != null) {
            int shedDepth = mm(shed.getDepth());
            int xStart    = x - shedDepth - thickness;
            shed(shed, xStart);
            placePostColumn(thickness, height, x, topRow, bottomRow);
            x = xStart;
            placePostColumn(thickness, height, x, topRow, bottomRow);
            numberOfColumns -= 1;
            placePostColumn(thickness, height, x, topRow, bottomRow);
            ruler(HORIZONTAL, shedDepth, x + thickness, PADDING / 2, formatCM(shedDepth / 10));
        }

        placePostColumn(thickness, height, x, topRow, bottomRow);

        int columnDistance = (x - END_OVERHANG_MM - PADDING) / numberOfColumns;
        for (int column = numberOfColumns; column >= 1; column--) {
            x -= columnDistance;
            placePostColumn(thickness, height, x, topRow, bottomRow);
            ruler(HORIZONTAL, columnDistance - thickness, x + thickness, PADDING / 2,
                    formatCM((columnDistance - thickness) / 10));
        }

        ruler(sideDocument, VERTICAL, height, PADDING / 2, PADDING + 195, formatCM(specification.getHeight()));
    }

    /**
     * Logs the materials needed to construct the shed. Draws the shed to the drawings.
     *
     * @param shed   The shed to construct and draw.
     * @param xStart The starting x position of the shed.
     */
    private void shed(Shed shed, int xStart)
    {
        Material materialCladding  = shedCladding.getMaterial();
        int      claddingThickness = materialCladding.getAttribute("THICKNESS_MM").getInt();
        int      claddingWidth     = materialCladding.getAttribute("WIDTH_MM").getInt();
        int      shedDepth         = mm(shed.getDepth());
        int      postThickness     = 97;
        int      space             = claddingWidth - (claddingWidth / 5 * 2);
        int      every             = claddingWidth + space;
        int      overlap           = (claddingWidth - space) / 2;
        int      offset            = claddingWidth - overlap;

        int numberOfEndBoards = (int) Math.ceil((double) (width - postThickness * 2) / every) - 1;
        int leftColumn        = xStart;
        int rightColumn       = xStart + shedDepth + 97 * 2 - claddingThickness;
        for (int endBoard = 0; endBoard < numberOfEndBoards; endBoard++) {
            int y = PADDING + SIDE_OVERHANG_MM + postThickness + (endBoard * every);
            rect(aerialDocument, claddingThickness, claddingWidth, leftColumn, y);
            rect(aerialDocument, claddingThickness, claddingWidth, leftColumn + claddingThickness, y + offset);
            rect(aerialDocument, claddingThickness, claddingWidth, rightColumn, y);
            rect(aerialDocument, claddingThickness, claddingWidth, rightColumn - claddingThickness, y + offset);
        }

        int numberOfSideBoards = (int) Math.ceil((double) shedDepth / every);
        int topRow             = PADDING + SIDE_OVERHANG_MM;
        int botRow             = topRow + width - claddingThickness;
        for (int sideBoard = 0; sideBoard < numberOfSideBoards; sideBoard++) {
            int x = xStart + postThickness + (sideBoard * every);
            rect(aerialDocument, claddingWidth, claddingThickness, x, topRow);
            rect(aerialDocument, claddingWidth, claddingThickness, x + offset, topRow + claddingThickness);
            rect(aerialDocument, claddingWidth, claddingThickness, x, botRow);
            rect(aerialDocument, claddingWidth, claddingThickness, x + offset, botRow - claddingThickness);
            rect(sideDocument, claddingWidth, height, x, PADDING + 195);
            rect(sideDocument, claddingWidth, height, x + offset, PADDING + 195);
        }

        materials.add(shedSideCladdingNogging.getMaterial(), -1, shedSideCladdingNogging.getNotes());
        materials.add(shedGableCladdingNogging.getMaterial(), -1, shedGableCladdingNogging.getNotes());
        materials.add(shedDoorNogging.getMaterial(), 1, shedDoorNogging.getNotes());
        materials.add(materialCladding, numberOfSideBoards * 2 + numberOfEndBoards * 2, shedCladding.getNotes());
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
        filledRect(sideDocument, thickness, height + 195, x, PADDING);
        filledRect(aerialDocument, thickness, thickness, x, topRow);
        filledRect(aerialDocument, thickness, thickness, x, bottomRow);
    }
}
