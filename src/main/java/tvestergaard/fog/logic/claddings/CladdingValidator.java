package tvestergaard.fog.logic.claddings;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.logic.claddings.CladdingError.*;

public class CladdingValidator
{

    /**
     * Validates the provided cladding blueprint.
     *
     * @param name                The name to validate.
     * @param description         The description to validate.
     * @param pricePerSquareMeter The price per square meter to validate.
     * @return The errors with the provided cladding information.
     */
    public Set<CladdingError> validate(String name, String description, int pricePerSquareMeter)
    {
        Set<CladdingError> errors = new HashSet<>();

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
