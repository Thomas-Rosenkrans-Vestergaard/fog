package tvestergaard.fog.logic.materials;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.logic.materials.MaterialError.*;

public class MaterialValidator
{

    /**
     * Validates the provided information about a material.
     *
     * @param number      The number of the material to validate.
     * @param description The description of the material to validate.
     * @param price       The price of the material to validate.
     * @param unit        The unit size of the material to validate.
     * @param width       The width of the material to validate.
     * @param height      The height of the material to validate.
     * @return The errors (if any) resulting from the provided information.
     */
    public Set<MaterialError> validate(String number, String description, int price, int unit, int width, int height)
    {
        Set<MaterialError> errors = new HashSet<>();

        if (number == null || number.isEmpty())
            errors.add(NUMBER_EMPTY);
        else if (number.length() > 12)
            errors.add(NUMBER_LONGER_THAN_12);

        if (description == null || description.isEmpty())
            errors.add(DESCRIPTION_EMPTY);
        else if (description.length() > 255)
            errors.add(DESCRIPTION_LONGER_THAN_255);

        if (width < 1)
            errors.add(WIDTH_LESS_THAN_1);

        if (height < 1)
            errors.add(HEIGHT_LESS_THAN_1);

        if (price < 0)
            errors.add(PRICE_NEGATIVE);

        return errors;
    }
}
