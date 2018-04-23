package tvestergaard.fog.data.flooring;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlFlooringDAO extends AbstractMysqlDAO implements FlooringDAO
{

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
     * Returns a complete list of the {@link Flooring}s in the system.
     *
     * @return The complete list of the {@link Flooring}s in the system.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Flooring> get() throws MysqlDataAccessException
    {
        try {
            final List<Flooring> floors = new ArrayList<>();
            final String         SQL    = "SELECT * FROM floors";
            try (java.sql.PreparedStatement statement = getConnection().prepareStatement(SQL)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    floors.add(createFlooring(resultSet));

                return floors;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Creates a new {@link Flooring} using the provided {@code ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the isntance of {@link Flooring}.
     * @return The newly created instance of {@link Flooring}.
     * @throws SQLException
     */
    private Flooring createFlooring(ResultSet resultSet) throws SQLException
    {
        return new MysqlFlooring(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("price_per_square_meter")
        );
    }

    /**
     * The {@link Flooring} implementation returned by the {@link MysqlFlooringDAO}.
     */
    private class MysqlFlooring implements Flooring
    {

        /**
         * The unique identifier of the {@link Flooring}.
         */
        private final int id;

        /**
         * The name of the {@link Flooring}.
         */
        private final String name;

        /**
         * The description of the {@link Flooring}.
         */
        private final String description;

        /**
         * The price of the {@link Flooring} per square meter (in øre).
         */
        private final int pricePerSquareMeter;

        /**
         * Creates a new {@link MysqlFlooringDAO}.
         *
         * @param id                  The unique identifier of the {@link Flooring}.
         * @param name                The name of the {@link Flooring}.
         * @param description         The description of the {@link Flooring}.
         * @param pricePerSquareMeter The price of the {@link Flooring} per square meter (in øre).
         */
        public MysqlFlooring(int id, String name, String description, int pricePerSquareMeter)
        {
            this.id = id;
            this.name = name;
            this.description = description;
            this.pricePerSquareMeter = pricePerSquareMeter;
        }

        /**
         * Returns the unique identifier of the {@link Flooring}.
         *
         * @return The unique identifier of the {@link Flooring}.
         */
        @Override public int getId()
        {
            return id;
        }

        /**
         * Returns the name of the {@link Flooring}.
         *
         * @return The name of the {@link Flooring}.
         */
        @Override public String getName()
        {
            return name;
        }

        /**
         * Returns the description of the {@link Flooring}.
         *
         * @return The description of the {@link Flooring}.
         */
        @Override public String getDescription()
        {
            return description;
        }

        /**
         * Returns the price of the {@link Flooring} per square meter (in øre).
         *
         * @return The price of the {@link Flooring} per square meter (in øre).
         */
        @Override public int getPricePerSquareMeter()
        {
            return pricePerSquareMeter;
        }

        @Override public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MysqlFlooring that = (MysqlFlooring) o;

            return id == that.id;
        }

        @Override public int hashCode()
        {
            return id;
        }
    }
}
