package tvestergaard.fog.data.sheds;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlShedDAO extends AbstractMysqlDAO implements ShedDAO
{

    /**
     * Creates a new {@link MysqlShedDAO}.
     *
     * @param source The source being acted upon.
     */
    public MysqlShedDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns a complete list of the {@link Shed}s in the system.
     *
     * @return The complete list of the {@link Shed}s.
     * @throws MysqlDataAccessException When an exception occurs during the operation.
     */
    @Override public List<Shed> get() throws MysqlDataAccessException
    {
        try {
            final List<Shed> sheds = new ArrayList<>();
            final String     SQL   = "SELECT * FROM sheds";
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    sheds.add(createShed(resultSet));

                return sheds;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
