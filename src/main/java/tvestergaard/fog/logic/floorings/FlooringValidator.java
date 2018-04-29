package tvestergaard.fog.logic.floorings;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.logic.floorings.FlooringError.*;

public class FlooringValidator
{

    /**
     * Validates the provided flooring blueprint.
     *
     * @param name                The name of the flooring to validate.
     * @param description         The description of the flooring to validate.
     * @param pricePerSquareMeter The price per square meter to validate.
     * @return The errors with the provided flooring information.
     */
    public Set<FlooringError> validate(String name, String description, int pricePerSquareMeter)
    {
        Set<FlooringError> errors = new HashSet<>();

        if (name == null || name.isEmpty())
            errors.add(EMPTY_NAME);

        if (name != null && name.length() > 255)
            errors.add(NAME_LONGER_THAN_255);

        if (description == null || name.isEmpty())
            errors.add(EMPTY_DESCRIPTION);

        if (pricePerSquareMeter < 1)
            errors.add(NEGATIVE_PRICE);

        return errors;
    }
}
