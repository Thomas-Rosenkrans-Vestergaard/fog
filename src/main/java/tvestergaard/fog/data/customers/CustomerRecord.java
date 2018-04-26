package tvestergaard.fog.data.customers;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The default {@link Customer} implementation.
 */
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
     * The preferred contact method of the customer.
     */
    private ContactMethod contactMethod;

    /**
     * Whether or not the customer is active.
     */
    private boolean active;

    /**
     * The moment in time when the customer was created.
     */
    private LocalDateTime createdAt;

    /**
     * Creates a new customer.
     *
     * @param id            The unique identifier of the customer.
     * @param name          The name of the customer.
     * @param address       The address of the customer.
     * @param email         The email address of the customer.
     * @param phone         The phone number of the customer.
     * @param password      The password of the customer.
     * @param contactMethod The preferred contact method of the customer.
     * @param active        Whether or not the customer is active.
     * @param createdAt     The moment in time when the customer was created.
     */
    public CustomerRecord(int id, String name, String address, String email, String phone, String password,
                          ContactMethod contactMethod, boolean active, LocalDateTime createdAt)
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
     * Returns the unique identifier of the customer.
     *
     * @return The unique identifier of the customer.
     */
    @Override
    public int getId()
    {
        return id;
    }

    /**
     * Returns the name of the customer.
     *
     * @return The name of the customer.
     */
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the customer.
     *
     * @param name The new name.
     */
    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the address of the customer.
     *
     * @return The address of the customer.
     */
    @Override
    public String getAddress()
    {
        return address;
    }

    /**
     * Sets the address of the customer.
     *
     * @param address The new address.
     */
    @Override
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Returns the email address of the customer.
     *
     * @return The email address of the customer.
     */
    @Override
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the email address of the customer.
     *
     * @param email The new email.
     */
    @Override
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Returns the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    @Override
    public String getPhone()
    {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     *
     * @param phone The new phone number.
     */
    @Override
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * Returns the password of the customer.
     *
     * @return The password of the customer.
     */
    @Override
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password of the customer.
     *
     * @param password The new password.
     */
    @Override
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Returns the contact method preferred by the customer.
     *
     * @return The contact method preferred by the customer.
     */
    @Override
    public ContactMethod getContactMethod()
    {
        return this.contactMethod;
    }

    /**
     * Sets the contact method preferred by the customer.
     *
     * @param method The new contact method.
     */
    @Override
    public void setContactMethod(ContactMethod method)
    {
        this.contactMethod = method;
    }

    /**
     * Returns {@code true} if the customer is active.
     *
     * @return {@code true} if the customer is active.
     */
    @Override
    public boolean isActive()
    {
        return active;
    }

    /**
     * Sets the active state of the customer.
     *
     * @param state The new state.
     */
    @Override
    public void setActive(boolean state)
    {
        this.active = state;
    }

    /**
     * Returns the {@code LocalDateTime} representing the moment when the customer was created.
     *
     * @return The {@code LocalDateTime} representing the moment when the customer was created.
     */
    @Override
    public LocalDateTime getCreatedAt()
    {
        return this.createdAt;
    }

    @Override
    public boolean equals(Object o)
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

    @Override
    public int hashCode()
    {
        return Objects.hash(getId());
    }
}
