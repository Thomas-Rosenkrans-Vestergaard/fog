package tvestergaard.fog.data.customers;

public interface CustomerUpdater extends CustomerBlueprint
{

    /**
     * Returns a new customer updater from the provided information.
     *
     * @param id       The id of the customer to specify in the updater.
     * @param name     The name of the customer to specify in the updater.
     * @param address  The address of the customer to specify in the updater.
     * @param email    The email of the customer to specify in the updater.
     * @param phone    The phone of the customer to specify in the updater.
     * @param phone    The phone of the customer to specify in the updater.
     * @param password The password of the customer to specify in the updater.
     * @param active   Whether or not the customer to specify in the updater is active.
     * @param verified Whether or not the customer has verified their email.
     * @return The newly created customer updater.
     */
    static CustomerUpdater from(int id, String name, String address, String email, String phone, String password, boolean active, boolean verified)
    {
        return new CustomerRecord(id, name, address, email, phone, password, active, verified, null, null);
    }

    /**
     * Returns the unique identifier of the customer.
     *
     * @return The unique identifier of the customer.
     */
    int getId();

    /**
     * Returns whether or not the customer has verified their membership using their email address.
     *
     * @return {@code true} if the customer has verified their membership using their email address.
     */
    boolean isVerified();
}
