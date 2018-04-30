package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.flooring.Flooring;

public interface Shed extends ShedUpdater
{

    /**
     * Returns the cladding used on the shed.
     *
     * @return The cladding used on the shed.
     */
    Cladding getCladding();

    /**
     * Returns the flooring used on the shed.
     *
     * @return The flooring used on the shed.
     */
    Flooring getFlooring();

    /**
     * Checks that this shed equals another provided object. The two objects are only considered equal when all the
     * attributes of the two sheds are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the shed.
     *
     * @return The id of the shed.
     */
    int hashCode();
}
