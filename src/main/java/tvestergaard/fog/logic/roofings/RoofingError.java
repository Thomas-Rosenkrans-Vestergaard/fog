package tvestergaard.fog.logic.roofings;

public enum RoofingError
{
    EMPTY_NAME,
    EMPTY_DESCRIPTION,
    NAME_LONGER_THAN_255,
    MINIMUM_SLOPE_LESS_THAN_1,
    MINIMUM_SLOPE_GREATER_THAN_89,
    MAXIMUM_SLOPE_LESS_THAN_1,
    MAXIMUM_SLOPE_GREATER_THAN_89,
    MINIMUM_GREATER_THAN_MAXIMUM
}
