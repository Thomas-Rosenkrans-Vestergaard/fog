package tvestergaard.fog.data.customers;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlCustomerDAO extends AbstractMysqlDAO implements CustomerDAO
{

    /**
     * Creates a new {@link MysqlCustomerDAO}.
     *
     * @param source The source {@link MysqlCustomerDAO}.
     */
    public MysqlCustomerDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns a complete list of the {@link Customer}s in the system.
     *
     * @return The complete list.
     * @throws MysqlDataAccessException When an exception occurs during the operation.
     */
    @Override public List<Customer> get() throws MysqlDataAccessException
    {
        try {
            final List<Customer> customers = new ArrayList<>();
            final String         SQL       = "SELECT * FROM customers";
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    customers.add(createCustomer(resultSet));

                return customers;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Creates a new {@link Customer} instance from the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the {@link Customer} instance.
     * @return The newly created {@link Customer} instance.
     * @throws SQLException
     */
    private Customer createCustomer(ResultSet resultSet) throws SQLException
    {
        return new MysqlCustomer(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("address"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getString("password"),
                Customer.ContactMethod.from(resultSet.getInt("contact_method")),
                resultSet.getBoolean("active")

        );
    }

    /**
     * The {@link Customer} implementation returned by {@link MysqlCustomerDAO} instances.
     */
    private class MysqlCustomer implements Customer
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
        private String phoneNumber;

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
         * @param phoneNumber   The phone number of the {@link Customer}.
         * @param password      The password of the {@link Customer}.
         * @param contactMethod The preferred contact method of the {@link Customer}.
         * @param active        Whether or not the {@link Customer} is active.
         */
        public MysqlCustomer(int id, String name, String address, String email, String phoneNumber, String password, ContactMethod contactMethod, boolean active)
        {
            this.id = id;
            this.name = name;
            this.address = address;
            this.email = email;
            this.phoneNumber = phoneNumber;
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
            return 0;
        }

        /**
         * Returns the name of the {@link Customer}.
         *
         * @return The name of the {@link Customer}.
         */
        @Override public String getName()
        {
            return null;
        }

        /**
         * Sets the name of the {@link Customer}.
         *
         * @param name The new name.
         */
        @Override public void setName(String name)
        {

        }

        /**
         * Returns the address of the {@link Customer}.
         *
         * @return The address of the {@link Customer}.
         */
        @Override public String getAddress()
        {
            return null;
        }

        /**
         * Sets the address of the {@link Customer}.
         *
         * @param address The new address.
         */
        @Override public void setAddress(String address)
        {

        }

        /**
         * Returns the email address of the {@link Customer}.
         *
         * @return The email address of the {@link Customer}.
         */
        @Override public String getEmail()
        {
            return null;
        }

        /**
         * Sets the email address of the {@link Customer}.
         *
         * @param email The new email.
         */
        @Override public void setEmail(String email)
        {

        }

        /**
         * Returns the phone number of the {@link Customer}.
         *
         * @return The phone number of the {@link Customer}.
         */
        @Override public String getPhoneNumber()
        {
            return null;
        }

        /**
         * Sets the phone number of the {@link Customer}.
         *
         * @param phoneNumber The new phone number.
         */
        @Override public void setPhoneNumber(String phoneNumber)
        {

        }

        /**
         * Returns the password of the {@link Customer}.
         *
         * @return The password of the {@link Customer}.
         */
        @Override public String getPassword()
        {
            return null;
        }

        /**
         * Sets the password of the {@link Customer}.
         *
         * @param password The new password.
         */
        @Override public void setPassword(String password)
        {

        }

        /**
         * Returns the contact method preferred by the {@link Customer}.
         *
         * @return The contact method preferred by the {@link Customer}.
         */
        @Override public ContactMethod getContactMethod()
        {
            return null;
        }

        /**
         * Sets the contact method preferred by the {@link Customer}.
         *
         * @param method The new contact method.
         */
        @Override public void setContactMethod(ContactMethod method)
        {

        }
    }
}
