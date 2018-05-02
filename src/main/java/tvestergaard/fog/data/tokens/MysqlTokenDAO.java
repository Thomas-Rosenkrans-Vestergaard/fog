package tvestergaard.fog.data.tokens;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.ProductionDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MysqlTokenDAO extends AbstractMysqlDAO implements TokenDAO
{

    /**
     * Creates a new {@link MysqlTokenDAO}.
     *
     * @param source The {@link MysqlDataSource} being acted upon.
     */
    public MysqlTokenDAO(MysqlDataSource source)
    {
        super(source);
    }

    public MysqlTokenDAO()
    {
        this(ProductionDataSource.getSource());
    }

    /**
     * Inserts a new registration token into the data storage.
     *
     * @param customer The customer the token is issued to.
     * @param token    The token to insert.
     * @param use      The usage of the token to create.
     * @return An instance representing the newly inserted token.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Token create(int customer, String token, Use use) throws MysqlDataAccessException
    {
        final String SQL = "INSERT INTO tokens (`customer`, `hash`, `use`) VALUES (?, ?, ?);";
        try {
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, RETURN_GENERATED_KEYS)) {
                statement.setInt(1, customer);
                statement.setString(2, token);
                statement.setString(3, use.name());
                statement.executeUpdate();
                connection.commit();
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                return get(generated.getInt(1));
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the registration token with the provided id.
     *
     * @param id The id of the registration token to return.
     * @return The registration token with the provided id. Returns {@code null} in case no registration token with the
     * provided id exists.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Token get(int id) throws MysqlDataAccessException
    {
        final String SQL = "SELECT * FROM tokens INNER JOIN customers ON customer = customers.id WHERE tokens.id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.first();
            return createToken(resultSet);
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Creates a new registration token using the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the registration token.
     * @return The newly created registration token.
     * @throws SQLException
     */
    private Token createToken(ResultSet resultSet) throws SQLException
    {
        return new TokenRecord(
                resultSet.getInt("id"),
                createCustomer("customers", resultSet),
                resultSet.getString("hash"),
                Use.valueOf(resultSet.getString("use")),
                resultSet.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
