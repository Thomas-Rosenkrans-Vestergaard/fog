package tvestergaard.fog.data.customers;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The default {@link Customer} implementation.
 */
public class CustomerRecord implements Customer
{

    /**
     * The unique identifier of the {@link Customer}.
     */
    private final int id;

    /**
     * The name of the {@link Customer}.
     */
    private String name;

    /**
     * The address of the {@link Customer}.
     */
    private String address;

    /**
     * The email address of the {@link Customer}.
     */
    private String email;

    /**
     * The phone number of the {@link Customer}.
     */
    private String phone;

    /**
     * The password of the {@link Customer}.
     */
    private String password;

    /**
     * The preferred contact method of the {@link Customer}.
     */
    private ContactMethod contactMethod;

    /**
     * Whether or not the {@link Customer} is active.
     */
    private boolean active;

    /**
     * The moment in time when the {@link Customer} was created.
     */
    private LocalDateTime createdAt;

    /**
     * Creates a new {@link Customer}.
     *
     * @param id            The unique identifier of the {@link Customer}.
     * @param name          The name of the {@link Customer}.
     * @param address       The address of the {@link Customer}.
     * @param email         The email address of the {@link Customer}.
     * @param phone         The phone number of the {@link Customer}.
     * @param password      The password of the {@link Customer}.
     * @param contactMethod The preferred contact method of the {@link Customer}.
     * @param active        Whether or not the {@link Customer} is active.
     * @param createdAt     The moment in time when the {@link Customer} was created.
     */
    public CustomerRecord(int id, String name, String address, String email, String phone, String password, ContactMethod contactMethod, boolean active, LocalDateTime createdAt)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.contactMethod = contactMethod;
        this.active = active;
        this.createdAt = createdAt;
    }

    /**
     * Returns the unique identifier of the {@link Customer}.
     *
     * @return The unique identifier of the {@link Customer}.
     */
    @Override public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the {@link Customer}.
     *
     * @return The name of the {@link Customer}.
     */
    @Override public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the {@link Customer}.
     *
     * @param name The new name.
     */
    @Override public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the address of the {@link Customer}.
     *
     * @return The address of the {@link Customer}.
     */
    @Override public String getAddress()
    {
        return address;
    }

    /**
     * Sets the address of the {@link Customer}.
     *
     * @param address The new address.
     */
    @Override public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Returns the email address of the {@link Customer}.
     *
     * @return The email address of the {@link Customer}.
     */
    @Override public String getEmail()
    {
        return email;
    }

    /**
     * Sets the email address of the {@link Customer}.
     *
     * @param email The new email.
     */
    @Override public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Returns the phone number of the {@link Customer}.
     *
     * @return The phone number of the {@link Customer}.
     */
    @Override public String getPhone()
    {
        return phone;
    }

    /**
     * Sets the phone number of the {@link Customer}.
     *
     * @param phone The new phone number.
     */
    @Override public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * Returns the password of the {@link Customer}.
     *
     * @return The password of the {@link Customer}.
     */
    @Override public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password of the {@link Customer}.
     *
     * @param password The new password.
     */
    @Override public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Returns the contact method preferred by the {@link Customer}.
     *
     * @return The contact method preferred by the {@link Customer}.
     */
    @Override public ContactMethod getContactMethod()
    {
        return this.contactMethod;
    }

    /**
     * Sets the contact method preferred by the {@link Customer}.
     *
     * @param method The new contact method.
     */
    @Override public void setContactMethod(ContactMethod method)
    {
        this.contactMethod = method;
    }

    /**
     * Returns {@code true} if the {@link Customer} is active.
     *
     * @return {@code true} if the {@link Customer} is active.
     */
    @Override public boolean isActive()
    {
        return active;
    }

    /**
     * Sets the active state of the {@link Customer}.
     *
     * @param state The new state.
     */
    @Override public void setActive(boolean state)
    {
        this.active = state;
    }

    /**
     * Returns the {@code LocalDateTime} representing the moment when the {@link Customer} was created.
     *
     * @return The {@code LocalDateTime} representing the moment when the {@link Customer} was created.
     */
    @Override public LocalDateTime getCreatedAt()
    {
        return this.createdAt;
    }

    @Override public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer that = (Customer) o;
        return id == that.getId() &&
                active == that.isActive() &&
                Objects.equals(name, that.getName()) &&
                Objects.equals(address, that.getAddress()) &&
                Objects.equals(email, that.getEmail()) &&
                Objects.equals(phone, that.getPhone()) &&
                Objects.equals(password, that.getPassword()) &&
                contactMethod == that.getContactMethod() &&
                Objects.equals(createdAt, that.getCreatedAt());
    }

    @Override public int hashCode()
    {
        return Objects.hash(getId());
    }
}
