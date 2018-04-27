package tvestergaard.fog.logic.claddings;

import tvestergaard.fog.data.cladding.CladdingBlueprint;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.logic.claddings.CladdingError.*;

public class CladdingValidator
{

    /**
     * Validates the provided cladding blueprint.
     *
     * @param blueprint The cladding blueprint to perform validation upon.
     * @return The errors with the provided cladding information.
     */
    public Set<CladdingError> validate(CladdingBlueprint blueprint)
    {
        Set<CladdingError> errors = new HashSet<>();

        String name                = blueprint.getName();
        String description         = blueprint.getDescription();
        int    pricePerSquareMeter = blueprint.getPricePerSquareMeter();

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
