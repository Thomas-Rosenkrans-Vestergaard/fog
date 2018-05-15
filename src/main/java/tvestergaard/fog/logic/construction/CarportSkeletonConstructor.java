package tvestergaard.fog.logic.construction;

import org.w3c.dom.Document;
import tvestergaard.fog.data.components.Component;
import tvestergaard.fog.data.materials.Material;
import tvestergaard.fog.data.orders.Shed;

import static tvestergaard.fog.logic.construction.DrawingUtilities.Rotation.HORIZONTAL;
import static tvestergaard.fog.logic.construction.DrawingUtilities.Rotation.VERTICAL;

public class CarportSkeletonConstructor extends DrawingUtilities implements SkeletonConstructor
{

    private static final int SIDE_OVERHANG_MM = 250;
    private static final int END_OVERHANG_MM  = 500;
    private static final int PADDING          = 1000;

    private int              length;
    private int              outerLength;
    private int              width;
    private int              outerWidth;
    private int              height;
    private MutableMaterials materials;
    private Document         aerialDocument;
    private Document         sideDocument;
    private CAR01Components  components;

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
        Component component = components.getGaragePosts();
        Material  material  = component.getMaterial();

        final int thickness = material.getAttribute("THICKNESS_MM").getInt();
        final int width     = material.getAttribute("WIDTH_MM").getInt();

        rect(sideDocument, this.outerLength, width, PADDING, PADDING);
        rect(aerialDocument, this.outerLength, thickness, PADDING, PADDING + SIDE_OVERHANG_MM);
        rect(aerialDocument, this.outerLength, thickness, PADDING, this.width + PADDING + SIDE_OVERHANG_MM - 97 / 2);

        ruler(aerialDocument, HORIZONTAL, this.outerLength, PADDING, this.outerWidth + PADDING + PADDING / 2, formatCM(outerLength / 10));
        ruler(aerialDocument, VERTICAL, this.width, PADDING / 2, PADDING + SIDE_OVERHANG_MM, formatCM(specification.getWidth()));
        ruler(aerialDocument, HORIZONTAL, END_OVERHANG_MM, PADDING, PADDING / 2, formatCM(END_OVERHANG_MM / 10));
        ruler(aerialDocument, HORIZONTAL, END_OVERHANG_MM, PADDING + outerLength - END_OVERHANG_MM, PADDING / 2, formatCM(END_OVERHANG_MM / 10));
    }

    /**
     * Draws and adds the garage posts to the material list.
     *
     * @param specification The specification
     */
    private void posts(ConstructionSpecification specification)
    {
        Component component = components.getGaragePosts();
        Shed      shed      = specification.getShed();
        int       thickness = component.getMaterial().getAttribute("THICKNESS_MM").getInt();
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

        ruler(aerialDocument, HORIZONTAL,
                secondColumn - firstColumn - thickness,
                firstColumn + thickness, PADDING / 2,
                formatCM((secondColumn - firstColumn) / 10));

        ruler(aerialDocument, HORIZONTAL, (x - firstColumn) / 2 - thickness, secondColumn + thickness, PADDING / 2,
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
}
