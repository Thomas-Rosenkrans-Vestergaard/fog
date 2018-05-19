package tvestergaard.fog.data.purchases.bom;

public interface BomDrawingBlueprint
{

    /**
     * Returns the header of the drawing.
     *
     * @return The header of the drawing.
     */
    String getTitle();

    /**
     * Returns the svg contents of the drawing.
     *
     * @return The svg contents of the drawing.
     */
    String getContent();
}
