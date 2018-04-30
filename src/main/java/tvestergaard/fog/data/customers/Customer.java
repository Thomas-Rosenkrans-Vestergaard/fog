package tvestergaard.fog.data.customers;

public interface Customer extends CustomerUpdater
{

    /**
     * Returns whether or not the customer has confirmed their membership using their email address.
     *
     * @return {@code true} if the customer has confirmed their membership using their email address.
     */
    boolean isConfirmed();

    /**
     * Checks that this customer equals another provided object. The two objects are only considered equal when all the
     * attributes of the two customers are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the customer.
     *
     * @return The id of the customer.
     */
    int hashCode();
}
