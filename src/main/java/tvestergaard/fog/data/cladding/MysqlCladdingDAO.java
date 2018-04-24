package tvestergaard.fog.data.cladding;

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

public class MysqlCladdingDAO extends AbstractMysqlDAO implements CladdingDAO
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
     * Creates a new {@link MysqlCladdingDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlCladdingDAO}.
     */
    public MysqlCladdingDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the {@link Cladding}s in the system.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Cladding}s.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Cladding> get(Constraint... constraints) throws MysqlDataAccessException
    {
        try {
            final List<Cladding> floors = new ArrayList<>();
            final String         SQL    = generator.generate("SELECT * FROM floors", constraints);
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    floors.add(createCladding(resultSet));

                return floors;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
