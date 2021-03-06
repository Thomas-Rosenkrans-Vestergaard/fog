package tvestergaard.fog.logic.customers;

public enum CustomerError
{
    NAME_EMPTY,
    NAME_LONGER_THAN_255,
    ADDRESS_EMPTY,
    ADDRESS_LONGER_THAN_255,
    EMAIL_INVALID,
    EMAIL_LONGER_THAN_255,
    EMAIL_TAKEN,
    PHONE_EMPTY,
    PHONE_LONGER_THAN_30,
    PASSWORD_SHORTER_THAN_4
}
