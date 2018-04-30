package tvestergaard.fog.data.customers;

import java.time.LocalDateTime;

public interface CustomerBlueprint
{

    /**
     * Returns a new customer blueprint from the provided information.
     *
     * @param name     The name of the customer to specify in the blueprint.
     * @param address  The address of the customer to specify in the blueprint.
     * @param email    The email of the customer to specify in the blueprint.
     * @param phone    The phone of the customer to specify in the blueprint.
     * @param phone    The phone of the customer to specify in the blueprint.
     * @param password The password of the customer to specify in the blueprint.
     * @param active   Whether or not the customer to specify in the blueprint is active.
     * @return The newly created customer blueprint.
     */
    static CustomerBlueprint from(String name, String address, String email, String phone, String password, boolean active)
    {
        return new CustomerRecord(-1, name, address, email, phone, password, active, false, null);
    }

    /**
     * Returns the name of the customer.
     *
     * @return The name of the customer.
     */
    String getName();

    /**
     * Sets the name of the customer.
     *
     * @param name The new name.
     */
    void setName(String name);

    /**
     * Returns the address of the customer.
     *
     * @return The address of the customer.
     */
    String getAddress();

    /**
     * Sets the address of the customer.
     *
     * @param address The new address.
     */
    void setAddress(String address);

    /**
     * Returns the email address of the customer.
     *
     * @return The email address of the customer.
     */
    String getEmail();

    /**
     * Sets the email address of the customer.
     *
     * @param email The new email.
     */
    void setEmail(String email);

    /**
     * Returns the phone number of the customer.
     *
     * @return The phone number of the customer.
     */
    String getPhone();

    /**
     * Sets the phone number of the customer.
     *
     * @param phoneNumber The new phone number.
     */
    void setPhone(String phoneNumber);

    /**
     * Returns the password of the customer.
     *
     * @return The password of the customer.
     */
    String getPassword();

    /**
     * Sets the password of the customer.
     *
     * @param password The new password.
     */
    void setPassword(String password);

    /**
     * Returns {@code true} if the customer is active.
     *
     * @return {@code true} if the customer is active.
     */
    boolean isActive();

    /**
     * Sets the active state of the customer.
     *
     * @param state The new state.
     */
    void setActive(boolean state);

    /**
     * Returns the {@code LocalDateTime} representing the moment when the customer was created.
     *
     * @return The {@code LocalDateTime} representing the moment when the customer was created.
     */
    LocalDateTime getCreatedAt();
}
