package tvestergaard.fog.data.tokens;

import tvestergaard.fog.data.customers.Customer;

import java.time.LocalDateTime;

public interface Token
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
     * Returns the use of the token.
     *
     * @return The use of the token.
     */
    TokenUse getUse();

    /**
     * Returns the moment in time when the token was created.
     *
     * @return The moment in time when the token was created.
     */
    LocalDateTime getCreatedAt();
}
