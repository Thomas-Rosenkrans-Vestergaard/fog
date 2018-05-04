package tvestergaard.fog.logic.roofings;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.logic.roofings.RoofingError.*;

public class RoofingValidator
{

    /**
     * Validates the provided roofing blueprint.
     *
     * @param name        The name to validate.
     * @param description The description to validate.
     * @return The errors with the provided roofing information.
     */
    public Set<RoofingError> validate(String name, String description)
    {
        Set<RoofingError> errors = new HashSet<>();

        if (name == null || name.isEmpty())
            errors.add(EMPTY_NAME);

        if (name != null && name.length() > 255)
            errors.add(NAME_LONGER_THAN_255);

        if (description == null || description.isEmpty())
            errors.add(EMPTY_DESCRIPTION);

        return errors;
    }
}
