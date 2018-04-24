package tvestergaard.fog.data.sheds;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.flooring.Flooring;

public interface Shed
{

    /**
     * Returns the unique identifier of the {@link Shed}.
     *
     * @return The unique identifier of the {@link Shed}.
     */
    int getId();

    /**
     * Returns the width of the {@link Shed}.
     *
     * @return The width of the {@link Shed}.
     */
    int getWidth();

    /**
     * Sets the width of the {@link Shed}.
     *
     * @param width The new width.
     */
    void setWidth(int width);

    /**
     * Returns the depth of the {@link Shed}.
     *
     * @return The depth of the {@link Shed}.
     */
    int getDepth();

    /**
     * Sets the depth of the {@link Shed}.
     *
     * @param depth The new depth.
     */
    void setDepth(int depth);

    /**
     * Returns the {@link Cladding} used on the {@link Shed}.
     *
     * @return The {@link Cladding} used on the {@link Shed}.
     */
    Cladding getCladding();

    /**
     * Sets hte {@link Cladding} used on the {@link Shed}.
     *
     * @param cladding The new {@link Cladding}.
     */
    void setCladding(Cladding cladding);

    /**
     * Returns the {@link Flooring} used on the {@link Shed}.
     *
     * @return The {@link Flooring} used on the {@link Shed}.
     */
    Flooring getFlooring();

    /**
     * Sets the {@link Flooring} used on the {@link Shed}.
     *
     * @param flooring The new {@link Flooring}.
     */
    void setFlooring(Flooring flooring);
}