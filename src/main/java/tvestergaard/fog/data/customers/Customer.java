package tvestergaard.fog.data.customers;

public interface Customer extends CustomerUpdater
{

    /**
     * Returns whether or not the customer has confirmed their membership using their email address.
     *
     * @return {@code true} if the customer has confirmed their membership using their email address.
     */
    boolean isConfirmed();
}
