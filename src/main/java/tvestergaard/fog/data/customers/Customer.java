package tvestergaard.fog.data.customers;

import java.time.LocalDateTime;

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
    String getPhone();

    /**
     * Sets the phone number of the {@link Customer}.
     *
     * @param phoneNumber The new phone number.
     */
    void setPhone(String phoneNumber);

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
     * Returns {@code true} if the {@link Customer} is active.
     *
     * @return {@code true} if the {@link Customer} is active.
     */
    boolean isActive();

    /**
     * Sets the active state of the {@link Customer}.
     *
     * @param state The new state.
     */
    void setActive(boolean state);

    /**
     * Returns the {@code LocalDateTime} representing the moment when the {@link Customer} was created.
     *
     * @return The {@code LocalDateTime} representing the moment when the {@link Customer} was created.
     */
    LocalDateTime getCreatedAt();

    /**
     * Checks that this {@link Customer} equals another provided object. The two objects are only considered equal when
     * all the attributes of the two {@link Customer}s are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the {@link Customer}.
     *
     * @return The id of the {@link Customer}.
     */
    int hashCode();

    /**
     * Represents a way the {@link Customer} can be contacted.
     */
    enum ContactMethod
    {

        /**
         * The {@link Customer} prefers to be contacted by email.
         */
        EMAIL(0),

        /**
         * The {@link Customer} prefers to be contacted by phone.
         */
        PHONE(1);

        /**
         * The id representing the {@link ContactMethod}.
         */
        public final int id;

        /**
         * Creates a new {@link ContactMethod}.
         *
         * @param id The id representing the {@link ContactMethod}.
         */
        ContactMethod(int id)
        {
            this.id = id;
        }

        /**
         * Returns the {@link ContactMethod} with the provided identifier.
         *
         * @param id The identifier.
         * @return The {@link ContactMethod} with the provided identifier.
         */
        public static ContactMethod from(int id)
        {
            if (id == 0)
                return EMAIL;
            if (id == 1)
                return PHONE;

            throw new IllegalArgumentException("No contact method with provided id.");
        }
    }
}
