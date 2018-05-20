package tvestergaard.fog.logic.claddings;

import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;
import static tvestergaard.fog.Helpers.randomString;

public class CladdingValidatorTest
{

    private final CladdingValidator instance = new CladdingValidator();

    @Test
    public void validate() throws Exception
    {
        Set<CladdingError> errors = instance.validate(null, null);

        assertTrue(errors.contains(CladdingError.EMPTY_NAME));
        assertTrue(errors.contains(CladdingError.EMPTY_DESCRIPTION));

        errors = instance.validate("", "");

        assertTrue(errors.contains(CladdingError.EMPTY_NAME));
        assertTrue(errors.contains(CladdingError.EMPTY_DESCRIPTION));

        errors = instance.validate(randomString(256), null);

        assertTrue(errors.contains(CladdingError.NAME_LONGER_THAN_255));

        errors = instance.validate("Some name", "Some description");

        assertTrue(errors.isEmpty());
    }
}