package tvestergaard.fog.data.customers;

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
     */
    public CustomerRecord(int id, String name, String address, String email, String phone, String password, ContactMethod contactMethod, boolean active)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.contactMethod = contactMethod;
        this.active = active;
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
}
