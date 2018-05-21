package tvestergaard.fog.logic.roofings;

/**
 * Represents a validation error that can be caught by the {@link RoofingValidator}.
 */
public enum RoofingError
{
    EMPTY_NAME,
    EMPTY_DESCRIPTION,
    NAME_LONGER_THAN_255,
}
