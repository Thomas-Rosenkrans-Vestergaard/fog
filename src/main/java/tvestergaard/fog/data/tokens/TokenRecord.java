package tvestergaard.fog.data.tokens;

import tvestergaard.fog.data.customers.Customer;

import java.time.LocalDateTime;
import java.util.Objects;

public class TokenRecord implements Token
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
     * The use of the token.
     */
    private final TokenUse use;

    /**
     * The moment in time when the token was created.
     */
    private final LocalDateTime createdAt;

    /**
     * Creates a new {@link TokenRecord}.
     *
     * @param id        The unique identifier of the registration token.
     * @param customer  The customer the token was issued to.
     * @param hash      The hash of the token.
     * @param use       The use of the token.
     * @param createdAt The moment in time when the token was created.
     */
    public TokenRecord(int id, Customer customer, String hash, TokenUse use, LocalDateTime createdAt)
    {
        this.id = id;
        this.customer = customer;
        this.hash = hash;
        this.use = use;
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
     * Returns the use of the token.
     *
     * @return The use of the token.
     */
    @Override public TokenUse getUse()
    {
        return use;
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
        if (!(o instanceof Token)) return false;
        Token that = (Token) o;
        return getId() == that.getId() &&
                Objects.equals(getHash(), that.getHash()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }

    @Override public String toString()
    {
        return "TokenRecord{" +
                "id=" + id +
                ", customer=" + customer +
                ", hash='" + hash + '\'' +
                ", use=" + use +
                ", createdAt=" + createdAt +
                '}';
    }
}
