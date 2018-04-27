package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.sheds.ShedUpdater;

public interface OrderUpdater extends OrderBlueprint
{

    /**
     * Returns the unique identifier of the order.
     *
     * @return The unique identifier of the order.
     */
    int getId();

    /**
     * Returns the shed updater.
     *
     * @return The shed updater.
     */
    ShedUpdater getShed();
}
