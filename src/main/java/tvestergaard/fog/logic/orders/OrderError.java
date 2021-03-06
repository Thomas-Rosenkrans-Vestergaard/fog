package tvestergaard.fog.logic.orders;

/**
 * The possible reasons for throwing the {@link OrderValidatorException}.
 */
public enum OrderError
{
    INCOMPLETE_ORDER,
    UNKNOWN_CUSTOMER,
    INACTIVE_CUSTOMER,
    UNKNOWN_CLADDING,
    INACTIVE_CLADDING,
    ILLEGAL_WIDTH,
    ILLEGAL_LENGTH,
    ILLEGAL_HEIGHT,
    UNKNOWN_ROOFING,
    INACTIVE_ROOFING,
    ILLEGAL_SLOPE,
    ILLEGAL_SHED_DEPTH,
    UNKNOWN_SHED_CLADDING,
    INACTIVE_SHED_CLADDING,
    UNKNOWN_SHED_FLOORING,
    INACTIVE_SHED_FLOORING,
}
