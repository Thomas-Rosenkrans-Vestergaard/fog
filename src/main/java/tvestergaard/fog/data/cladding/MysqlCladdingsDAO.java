package tvestergaard.fog.data.cladding;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlCladdingsDAO extends AbstractMysqlDAO implements CladdingsDAO
{

    /**
     * Creates a new {@link MysqlCladdingsDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlCladdingsDAO}.
     */
    public MysqlCladdingsDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns a complete list of the {@link Cladding}s in the system.
     *
     * @return The complete list of the {@link Cladding}s in the system.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Cladding> getAll() throws MysqlDataAccessException
    {
        try {
            final List<Cladding> floors = new ArrayList<>();
            final String         SQL    = "SELECT * FROM floors";
            try (java.sql.PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    floors.add(createCladding(resultSet));

                return floors;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Creates a new {@link Cladding} using the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the isntance of {@link Cladding}.
     * @return The newly created instance of {@link Cladding}.
     * @throws SQLException
     */
    private Cladding createCladding(ResultSet resultSet) throws SQLException
    {
        return new MysqlCladding(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("price_per_square_meter")
        );
    }

    /**
     * The {@link Cladding} implementation returned by the {@link MysqlCladdingsDAO}.
     */
    private class MysqlCladding implements Cladding
    {

        /**
         * The unique identifier of the {@link Cladding}.
         */
        private final int id;

        /**
         * The name of the {@link Cladding}.
         */
        private final String name;

        /**
         * The description of the {@link Cladding}.
         */
        private final String description;

        /**
         * The price of the {@link Cladding} per square meter (in øre).
         */
        private final int pricePerSquareMeter;

        /**
         * Creates a new {@link MysqlCladdingsDAO}.
         *
         * @param id                  The unique identifier of the {@link Cladding}.
         * @param name                The name of the {@link Cladding}.
         * @param description         The description of the {@link Cladding}.
         * @param pricePerSquareMeter The price of the {@link Cladding} per square meter (in øre).
         */
        public MysqlCladding(int id, String name, String description, int pricePerSquareMeter)
        {
            this.id = id;
            this.name = name;
            this.description = description;
            this.pricePerSquareMeter = pricePerSquareMeter;
        }

        /**
         * Returns the unique identifier of the {@link Cladding}.
         *
         * @return The unique identifier of the {@link Cladding}.
         */
        @Override public int getId()
        {
            return id;
        }

        /**
         * Returns the name of the {@link Cladding}.
         *
         * @return The name of the {@link Cladding}.
         */
        @Override public String getName()
        {
            return name;
        }

        /**
         * Returns the description of the {@link Cladding}.
         *
         * @return The description of the {@link Cladding}.
         */
        @Override public String getDescription()
        {
            return description;
        }

        /**
         * Returns the price of the {@link Cladding} per square meter (in øre).
         *
         * @return The price of the {@link Cladding} per square meter (in øre).
         */
        @Override public int getPricePerSquareMeter()
        {
            return pricePerSquareMeter;
        }

        @Override public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MysqlCladding that = (MysqlCladding) o;

            return id == that.id;
        }

        @Override public int hashCode()
        {
            return id;
        }
    }
}
