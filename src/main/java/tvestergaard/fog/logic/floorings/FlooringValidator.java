package tvestergaard.fog.logic.floorings;

import tvestergaard.fog.data.flooring.FlooringBlueprint;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.logic.floorings.FlooringError.*;

public class FlooringValidator
{

    /**
     * Validates the provided flooring blueprint.
     *
     * @param blueprint The flooring blueprint to perform validation upon.
     * @return The errors with the provided flooring information.
     */
    public Set<FlooringError> validate(FlooringBlueprint blueprint)
    {
        Set<FlooringError> errors = new HashSet<>();

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
