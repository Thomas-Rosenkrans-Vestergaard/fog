package tvestergaard.fog.data.customers;

public interface Customer
{

    /**
     * Returns the unique identifier of the {@link Customer}.
     *
     * @return The unique identifier of the {@link Customer}.
     */
    int getId();

    /**
     * Returns the name of the {@link Customer}.
     *
     * @return The name of the {@link Customer}.
     */
    String getName();

    /**
     * Sets the name of the {@link Customer}.
     *
     * @param name The new name.
     */
    void setName(String name);

    /**
     * Returns the address of the {@link Customer}.
     *
     * @return The address of the {@link Customer}.
     */
    String getAddress();

    /**
     * Sets the address of the {@link Customer}.
     *
     * @param address The new address.
     */
    void setAddress(String address);

    /**
     * Returns the email address of the {@link Customer}.
     *
     * @return The email address of the {@link Customer}.
     */
    String getEmail();

    /**
     * Sets the email address of the {@link Customer}.
     *
     * @param email The new email.
     */
    void setEmail(String email);

    /**
     * Returns the phone number of the {@link Customer}.
     *
     * @return The phone number of the {@link Customer}.
     */
    String getPhoneNumber();

    /**
     * Sets the phone number of the {@link Customer}.
     *
     * @param phoneNumber The new phone number.
     */
    void setPhoneNumber(String phoneNumber);

    /**
     * Returns the password of the {@link Customer}.
     *
     * @return The password of the {@link Customer}.
     */
    String getPassword();

    /**
     * Sets the password of the {@link Customer}.
     *
     * @param password The new password.
     */
    void setPassword(String password);

    /**
     * Returns the contact method preferred by the {@link Customer}.
     *
     * @return The contact method preferred by the {@link Customer}.
     */
    ContactMethod getContactMethod();

    /**
     * Sets the contact method preferred by the {@link Customer}.
     *
     * @param method The new contact method.
     */
    void setContactMethod(ContactMethod method);

    /**
     * Represents a way the {@link Customer} can be contacted.
     */
    public enum ContactMethod
    {
        EMAIL(0),
        PHONE(1);

        private final int id;

        ContactMethod(int id)
        {
            this.id = id;
        }

        public static ContactMethod from(int id){
            if(id == 0)
                return EMAIL;

            if(id == 1)
                return PHONE;

            throw new IllegalArgumentException("No contact method with provided id.l");
        }
    }
}
