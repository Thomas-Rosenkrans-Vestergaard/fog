package tvestergaard.fog.data.customers;

import java.time.LocalDateTime;
import java.util.Objects;

public class RegistrationTokenRecord implements RegistrationToken
{
    /**
     * The unique identifier of the registration token.
     */
    private final int id;

    /**
     * The customer the token was issued to.
     */
    private final Customer customer;

    /**
     * The hash of the token.
     */
    private final String hash;

    /**
     * The moment in time when the token was created.
     */
    private final LocalDateTime createdAt;

    /**
     * Creates a new {@link RegistrationTokenRecord}.
     *
     * @param id        The unique identifier of the registration token.
     * @param customer  The customer the token was issued to.
     * @param hash      The hash of the token.
     * @param createdAt The moment in time when the token was created.
     */
    public RegistrationTokenRecord(int id, Customer customer, String hash, LocalDateTime createdAt)
    {
        this.id = id;
        this.customer = customer;
        this.hash = hash;
        this.createdAt = createdAt;
    }

    /**
     * Returns the unique identifier of the registration token.
     *
     * @return The unique identifier of the registration token.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the customer the token was issued to.
     *
     * @return The customer the token was issued to.
     */
    @Override public Customer getCustomer()
    {
        return customer;
    }

    /**
     * Returns the hash of the token.
     *
     * @return The hash of the token.
     */
    @Override public String getHash()
    {
        return hash;
    }

    /**
     * Returns the moment in time when the token was created.
     *
     * @return The moment in time when the token was created.
     */
    @Override public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof RegistrationToken)) return false;
        RegistrationToken that = (RegistrationToken) o;
        return getId() == that.getId() &&
                Objects.equals(getHash(), that.getHash()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }
}
