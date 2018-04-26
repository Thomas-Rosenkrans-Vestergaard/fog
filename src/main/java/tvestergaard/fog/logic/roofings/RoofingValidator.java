package tvestergaard.fog.logic.roofings;

import tvestergaard.fog.data.roofing.Roofing;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.logic.roofings.RoofingError.*;

public class RoofingValidator
{

    /**
     * Validates the provided roofing information.
     *
     * @param name                The name to validate.
     * @param description         The description to validate.
     * @param pricePerSquareMeter The price per square meter to validate.
     * @return The errors with the provided roofing information.
     */
    public Set<RoofingError> validate(String name, String description, int pricePerSquareMeter, int minimumSlope, int maximumSlope)
    {
        Set<RoofingError> errors = new HashSet<>();

        if (name == null || name.isEmpty())
            errors.add(EMPTY_NAME);

        if (name != null && name.length() > 255)
            errors.add(NAME_LONGER_THAN_255);

        if (description == null || name.isEmpty())
            errors.add(EMPTY_DESCRIPTION);

        if (pricePerSquareMeter < 1)
            errors.add(NEGATIVE_PRICE);

        if (minimumSlope < 1)
            errors.add(MINIMUM_SLOPE_LESS_THAN_1);

        if (minimumSlope > 89)
            errors.add(MINIMUM_SLOPE_GREATER_THAN_89);

        if (maximumSlope < 1)
            errors.add(MAXIMUM_SLOPE_LESS_THAN_1);

        if (maximumSlope > 89)
            errors.add(MAXIMUM_SLOPE_GREATER_THAN_89);

        if (minimumSlope >= maximumSlope)
            errors.add(MINIMUM_GREATER_THAN_MAXIMUM);

        return errors;
    }

    /**
     * Validates the provided roofing.
     *
     * @param roofing The roofing to validate.
     * @return The errors with the provided roofing.
     */
    public Set<RoofingError> validate(Roofing roofing)
    {
        return validate(
                roofing.getName(),
                roofing.getDescription(),
                roofing.getPricePerSquareMeter(),
                roofing.getMinimumSlope(),
                roofing.getMaximumSlope());
    }
}
