package tvestergaard.fog.data.sheds;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.flooring.Flooring;

public interface ShedUpdater extends ShedBlueprint
{

    /**
     * Returns the unique identifier of the shed.
     *
     * @return The unique identifier of the shed.
     */
    int getId();
}
