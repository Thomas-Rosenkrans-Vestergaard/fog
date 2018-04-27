package tvestergaard.fog.data.orders;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.sheds.ShedBlueprint;
import tvestergaard.fog.data.sheds.ShedRecord;

import java.time.LocalDateTime;

public interface Order extends OrderUpdater
{

    /**
     * Returns a new order blueprint from the provided information.
     *
     * @param customer     The customer of the order to specify in the blueprint.
     * @param cladding     The cladding of the order to specify in the blueprint.
     * @param width        The width of the order to specify in the blueprint.
     * @param length       The length of the order to specify in the blueprint.
     * @param height       The height of the order to specify in the blueprint.
     * @param roofing      The roofing of the order to specify in the blueprint.
     * @param slope        The slope of the order to specify in the blueprint.
     * @param rafterChoice The choice of rafters on the order to specify in the blueprint.
     * @return The newly created order blueprint.
     */
    static OrderBlueprint blueprint(Customer customer,
                                    Cladding cladding,
                                    int width,
                                    int length,
                                    int height,
                                    Roofing roofing,
                                    int slope,
                                    RafterChoice rafterChoice,
                                    ShedBlueprint shed)
    {
        return new OrderRecord(-1, customer, cladding, width, length, height, roofing, slope, rafterChoice,
                shed == null ? null : new ShedRecord(-1, shed.getWidth(), shed.getDepth(), shed.getCladding(), shed.getFlooring()),
                null);
    }

    /**
     * Returns a {@code LocalDateTime} representing the moment in time when the customer placed the {@link Order}.
     *
     * @return The {@code LocalDateTime}.
     */
    LocalDateTime getCreatedAt();

    /**
     * Checks that this order equals another provided object. The two objects are only considered equal when all the
     * attributes of the two orders are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the order.
     *
     * @return The id of the order.
     */
    int hashCode();
}
