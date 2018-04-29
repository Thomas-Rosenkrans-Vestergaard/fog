package tvestergaard.fog.logic.customers;

import org.apache.commons.validator.routines.EmailValidator;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.CustomerBlueprint;
import tvestergaard.fog.data.customers.CustomerDAO;
import tvestergaard.fog.data.customers.CustomerUpdater;

import java.util.HashSet;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.customers.CustomerColumn.EMAIL;
import static tvestergaard.fog.data.customers.CustomerColumn.ID;
import static tvestergaard.fog.logic.customers.CustomerError.*;

public class CustomerValidator
{

    /**
     * The {@link CustomerDAO} used to access the customer storage of the application.
     */
    private final CustomerDAO customerDAO;

    /**
     * Creates a new {@link CustomerValidator}.
     *
     * @param customerDAO The {@link CustomerDAO} used to access the customer storage of the application.
     */
    public CustomerValidator(CustomerDAO customerDAO)
    {
        this.customerDAO = customerDAO;
    }

    /**
     * Validates the provided customer blueprint.
     *
     * @param blueprint The blueprint to perform validation upon.
     * @return The errors with the provided information.
     * @throws DataAccessException When a data access exception occurs.
     * @see CustomerFacade#register(String, String, String, String, String, boolean)
     */
    public Set<CustomerError> validate(CustomerBlueprint blueprint)
            throws DataAccessException
    {
        Set<CustomerError> errors = validate(
                blueprint.getName(),
                blueprint.getAddress(),
                blueprint.getEmail(),
                blueprint.getPhone(),
                blueprint.getPassword());
        if (customerDAO.first(where(eq(EMAIL, blueprint.getEmail()))) != null)
            errors.add(EMAIL_TAKEN);

        return errors;
    }

    /**
     * Validates the provided customer updater.
     *
     * @param updater The customer updater to perform validation upon.
     * @return The errors with the provided information.
     * @throws DataAccessException When a data access exception occurs.
     * @see CustomerFacade#update(int, String, String, String, String, String, boolean)
     */
    public Set<CustomerError> validate(CustomerUpdater updater) throws DataAccessException
    {
        Set<CustomerError> errors = validate(
                updater.getName(),
                updater.getAddress(),
                updater.getEmail(),
                updater.getPhone(),
                updater.getPassword());

        if (customerDAO.first(where(eq(EMAIL, updater.getEmail()), and(not(ID, updater.getId())))) != null)
            errors.add(CustomerError.EMAIL_TAKEN);

        return errors;
    }

    /**
     * Validates the provided information for creation.
     *
     * @param name     The name of the customer to validate.
     * @param address  The address of the customer to validate.
     * @param email    The email of the customer to validate.
     * @param phone    The phone number of the customer to validate.
     * @param password The password of the customer to validate.
     * @return The errors with the provided information.
     */
    public Set<CustomerError> validate(String name, String address, String email, String phone, String password)
    {
        Set<CustomerError> reasons = new HashSet<>();

        if (name == null || name.isEmpty())
            reasons.add(NAME_EMPTY);
        else if (name.length() > 255)
            reasons.add(NAME_LONGER_THAN_255);

        if (address == null || address.isEmpty())
            reasons.add(ADDRESS_EMPTY);
        else if (address.length() > 255)
            reasons.add(ADDRESS_LONGER_THAN_255);

        if (email == null || !EmailValidator.getInstance().isValid(email))
            reasons.add(EMAIL_INVALID);
        else if (email.length() > 255)
            reasons.add(EMAIL_LONGER_THAN_255);

        if (phone == null || phone.isEmpty())
            reasons.add(PHONE_EMPTY);
        else if (phone.length() > 30)
            reasons.add(PHONE_LONGER_THAN_30);

        if (password != null && password.length() < 4)
            reasons.add(PASSWORD_SHORTER_THAN_4);

        return reasons;
    }
}
