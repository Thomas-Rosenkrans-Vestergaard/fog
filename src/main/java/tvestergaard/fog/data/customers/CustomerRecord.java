package tvestergaard.fog.data.customers;

import java.time.LocalDateTime;
import java.util.Objects;

public class CustomerRecord implements Customer
{

    /**
     * The unique identifier of the customer.
     */
    private final int id;

    /**
     * The name of the customer.
     */
    private String name;

    /**
     * The address of the customer.
     */
    private String address;

    /**
     * The email address of the customer.
     */
    private String email;

    /**
     * The phone number of the customer.
     */
    private String phone;

    /**
     * The password of the customer.
     */
    private String password;

    /**
     * Whether or not the customer is active.
     */
    private boolean active;

    /**
     * Whether or not the customer has verified their membership using their email address.
     */
    private boolean verified;

    /**
     * The moment in time when the customer was created.
     */
    private LocalDateTime createdAt;

    /**
     * The date time when the password was last updated.
     */
    private LocalDateTime passwordUpdatedAt;

    /**
     * Creates a new customer.
     *
     * @param id                The unique identifier of the customer.
     * @param name              The name of the customer.
     * @param address           The address of the customer.
     * @param email             The email address of the customer.
     * @param phone             The phone number of the customer.
     * @param password          The password of the customer.
     * @param active            Whether or not the customer is active.
     * @param verified          Whether or not the customer has verified their membership using their email address.
     * @param createdAt         The moment in time when the customer was created.
     * @param passwordUpdatedAt The date time when the password was last updated.
     */
    public CustomerRecord(int id, String name, String address, String email, String phone, String password,
                          boolean active, boolean verified, LocalDateTime createdAt, LocalDateTime passwordUpdatedAt)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.active = active;
        this.verified = verified;
        this.createdAt = createdAt;
        this.passwordUpdatedAt = passwordUpdatedAt;
    }

    /**
     * Returns the unique identifier of the customer.
     *
     * @return The unique identifier of the customer.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the customer.
     *
     * @return The name of the customer.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name The new name.
     */
    @Override public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the address of the customer.
     *
     * @return The address of the customer.
     */
    @Override public String getAddress()
    {
        return address;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address The new address.
     */
    @Override public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Returns the email address of the customer.
     *
     * @return The email address of the customer.
     */
    @Override public String getEmail()
    {
        return email;
    }

    /**
     * Sets the email address of the customer.
     *
     * @param email The new email.
     */
    @Override public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Returns the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    @Override public String getPhone()
    {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone The new phone number.
     */
    @Override public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * Returns the password of the customer.
     *
     * @return The password of the customer.
     */
    @Override public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password of the customer.
     *
     * @param password The new password.
     */
    @Override public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Returns {@code true} if the customer is active.
     *
     * @return {@code true} if the customer is active.
     */
    @Override public boolean isActive()
    {
        return active;
    }

    /**
     * Sets the active state of the customer.
     *
     * @param state The new state.
     */
    @Override public void setActive(boolean state)
    {
        this.active = state;
    }

    /**
     * Returns whether or not the customer has verified their membership using their email address.
     *
     * @return {@code true} if the customer has verified their membership using their email address.
     */
    @Override public boolean isVerified()
    {
        return verified;
    }

    /**
     * Returns the {@code LocalDateTime} representing the moment when the customer was created.
     *
     * @return The {@code LocalDateTime} representing the moment when the customer was created.
     */
    @Override public LocalDateTime getCreatedAt()
    {
        return this.createdAt;
    }

    /**
     * Returns the date time when the password of the customer was last updated.
     *
     * @return The date time when the password of the customer was last updated.
     */
    @Override public LocalDateTime getPasswordUpdatedAt()
    {
        return passwordUpdatedAt;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer that = (Customer) o;
        return getId() == that.getId() &&
                isActive() == that.isActive() &&
                verified == that.isVerified() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getAddress(), that.getAddress()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getCreatedAt(), that.getCreatedAt());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId(), getName(), getAddress(), getEmail(), getPhone(), getPassword(), isActive(), verified, getCreatedAt());
    }

    @Override public String toString()
    {
        return "CustomerRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", verified=" + verified +
                ", createdAt=" + createdAt +
                '}';
    }
}
