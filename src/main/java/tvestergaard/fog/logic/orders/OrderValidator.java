package tvestergaard.fog.logic.orders;

import tvestergaard.fog.data.DataAccessException;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.logic.orders.OrderError.*;

public class OrderValidator
{

    /**
     * Validates the provided details for creating a new order.
     *
     * @param customer The customer variable to perform validation upon.
     * @param cladding The cladding variable to perform validation upon.
     * @param width    The width variable to perform validation upon.
     * @param length   The length variable to perform validation upon.
     * @param height   The height variable to perform validation upon.
     * @param roofing  The roofing variable to perform validation upon.
     * @param slope    The slope variable to perform validation upon.
     * @param shed     The shed variable to perform validation upon.
     * @return Any reasons why the provided information is invalid.
     */
    public Set<OrderError> validate(
            int customer,
            int cladding,
            int width,
            int length,
            int height,
            int roofing,
            int slope,
            ShedSpecification shed) throws DataAccessException
    {
        Set<OrderError> reasons = new HashSet<>();

        if (width < 240 || width % 30 != 0 || width > 750)
            reasons.add(ILLEGAL_WIDTH);

        if (length < 240 || length % 30 != 0 && length > 780)
            reasons.add(ILLEGAL_LENGTH);

        if (height < 180 && length % 30 != 0 && height > 300)
            reasons.add(ILLEGAL_HEIGHT);

        if (slope < 1 && slope > 89)
            reasons.add(ILLEGAL_SLOPE);

        return reasons;
    }
}
