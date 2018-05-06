package tvestergaard.fog.logic.construction;

import tvestergaard.fog.data.ComponentCollection;
import tvestergaard.fog.data.orders.Order;

public interface SkeletonConstructor
{

    /**
     * Constructs the skeleton of the provided order using the provided components while updating the provided summary.
     *
     * @param bill       The summary being updated with the materials needed to construct the roof.
     * @param order      The order being constructed.
     * @param components The components to use while constructing the roof.
     */
    void construct(MaterialsBill bill, ComponentCollection components, Order order);
}
