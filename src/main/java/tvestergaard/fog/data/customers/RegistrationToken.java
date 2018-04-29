package tvestergaard.fog.data.customers;

import java.time.LocalDateTime;

public interface RegistrationToken
{

    /**
     * Returns the unique identifier of the registration token.
     *
     * @return The unique identifier of the registration token.
     */
    int getId();

    /**
     * Returns the customer the token was issued to.
     *
     * @return The customer the token was issued to.
     */
    Customer getCustomer();

    /**
     * Returns the hash of the token.
     *
     * @return The hash of the token.
     */
    String getHash();

    /**
     * Returns the moment in time when the token was created.
     *
     * @return The moment in time when the token was created.
     */
    LocalDateTime getCreatedAt();

    /**
     * Checks that this registration token equals another provided object. The two objects are considered equal when
     * all the attributes of the instances are equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the registration token.
     *
     * @return The id of the registration token.
     */
    int hashCode();
}
