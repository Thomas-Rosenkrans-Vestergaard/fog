package tvestergaard.fog.data.flooring;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlFlooringDAO extends AbstractMysqlDAO implements FlooringDAO
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
     * Creates a new {@link MysqlFlooringDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlFlooringDAO}.
     */
    public MysqlFlooringDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the {@link Flooring}s in the data storage.
     * The results can be constrained using the provided {@link Constraint}s.
     *
     * @param constraints The {@link Constraint}s that modify the resulting list.
     * @return The resulting {@link Flooring}s.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Flooring> get(Constraint... constraints) throws MysqlDataAccessException
    {
        try {
            final List<Flooring> floors = new ArrayList<>();
            final String         SQL    = generator.generate("SELECT * FROM floorings", constraints);
            try (java.sql.PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                binder.bind(statement, constraints);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    floors.add(createFlooring(resultSet));

                return floors;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
