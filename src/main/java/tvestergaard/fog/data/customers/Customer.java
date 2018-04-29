package tvestergaard.fog.data.customers;

public interface Customer extends CustomerUpdater
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
    static CustomerBlueprint blueprint(String name, String address, String email, String phone, String password, boolean active)
    {
        return new CustomerRecord(-1, name, address, email, phone, password, active, false, null);
    }

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
     * @return The newly created customer updater.
     */
    static CustomerUpdater updater(int id, String name, String address, String email, String phone, String password, boolean active)
    {
        return new CustomerRecord(id, name, address, email, phone, password, active, false, null);
    }

    /**
     * Returns whether or not the customer has confirmed their membership using their email address.
     *
     * @return {@code true} if the customer has confirmed their membership using their email address.
     */
    boolean isConfirmed();

    /**
     * Checks that this customer equals another provided object. The two objects are only considered equal when all the
     * attributes of the two customers are considered equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if the two objects are considered equal.
     */
    boolean equals(Object other);

    /**
     * Returns the id of the customer.
     *
     * @return The id of the customer.
     */
    int hashCode();
}
