package tvestergaard.fog.logic.construction;

public interface ConstructionDrawing
{

    /**
     * Returns The name of the drawing.
     *
     * @return The name of the drawing.
     */
    String getName();

    /**
     * Returns the svg contents of the drawing.
     *
     * @return The svg contents of the drawing.
     */
    String getSVG();
}
