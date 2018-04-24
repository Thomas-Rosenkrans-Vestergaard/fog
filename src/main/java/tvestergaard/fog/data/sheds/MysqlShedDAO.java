package tvestergaard.fog.data.sheds;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.contraints.Constraint;
import tvestergaard.fog.data.contraints.StatementBinder;
import tvestergaard.fog.data.contraints.StatementGenerator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlShedDAO extends AbstractMysqlDAO implements ShedDAO
{

    /**
     * The generator used to generate SQL for the {@link Constraint}s provided to this DAO.
     */
    private final StatementGenerator generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the {@link Constraint}s provided to this DAO.
     */
    private final StatementBinder binder = new StatementBinder();

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
     * Returns the {@link Shed}s in the system.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Shed}s.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Shed> get(Constraint... constraints) throws MysqlDataAccessException
    {
        try {
            final List<Shed> sheds = new ArrayList<>();
            final String     SQL   = generator.generate("SELECT * FROM sheds", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
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
