package tvestergaard.fog.data.roofings;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlRoofingsDAO implements RoofingsDAO
{

    /**
     * The {@link MysqlDataSource} that provides the connection used by the {@link MysqlRoofingsDAO}.
     */
    private final MysqlDataSource source;

    /**
     * Creates a new {@link MysqlRoofingsDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlRoofingsDAO}.
     */
    public MysqlRoofingsDAO(MysqlDataSource source)
    {
        this.source = source;
    }

    /**
     * Returns a complete list of the {@link Roofing}s in the system.
     *
     * @return The complete list of the {@link Roofing}s in the system.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Roofing> getAll() throws MysqlDataAccessException
    {
        try {
            List<Roofing> roofings = new ArrayList<>();
            final String  SQL      = "SELECT * FROM roofings";
            try (PreparedStatement statement = source.getConnection().prepareStatement(SQL)) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next())
                    roofings.add(createRoofing(resultSet));

                return roofings;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Creates a new {@link Roofing} implementation using the provided {@link ResultSet}.
     *
     * @param resultSet The {@code ResultSet} from which to create the {@link Roofing} implementation.
     * @return The resulting instance of {@link Roofing}.
     * @throws SQLException
     */
    private Roofing createRoofing(ResultSet resultSet) throws SQLException
    {
        return new MysqlRoofing(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("minimum_slope"),
                resultSet.getInt("maximum_slope"),
                resultSet.getInt("price_per_square_meter")
        );
    }

    public class MysqlRoofing implements Roofing
    {

        /**
         * The unique identifier of the {@link Roofing}.
         */
        private final int id;

        /**
         * The name of the {@link Roofing}.
         */
        private final String name;

        /**
         * The description of the {@link Roofing}.
         */
        private final String description;

        /**
         * The minimum slope the {@link Roofing} must have.
         */
        private final int minimumSlope;

        /**
         * The maximum slope the {@link Roofing} must have.
         */
        private final int maximumSlope;

        /**
         * The price of the {@link Roofing} per square meter (in øre).
         */
        private final int pricePerSquareMeter;

        /**
         * Creates a new {@link MysqlRoofing}.
         *
         * @param id                  The unique identifier of the {@link Roofing}.
         * @param name                The name of the {@link Roofing}.
         * @param description         The description of the {@link Roofing}.
         * @param minimumSlope        The minimum slope the {@link Roofing} must have.
         * @param maximumSlope        The maximum slope the {@link Roofing} must have.
         * @param pricePerSquareMeter The price of the {@link Roofing} per square meter (in øre).
         */
        public MysqlRoofing(int id, String name, String description, int minimumSlope, int maximumSlope, int pricePerSquareMeter)
        {
            this.id = id;
            this.name = name;
            this.description = description;
            this.minimumSlope = minimumSlope;
            this.maximumSlope = maximumSlope;
            this.pricePerSquareMeter = pricePerSquareMeter;
        }

        /**
         * Returns the unique identifier of the {@link Roofing}.
         *
         * @return The unique identifier of the {@link Roofing}.
         */
        @Override public int getId()
        {
            return 0;
        }

        /**
         * Returns the name of the {@link Roofing}.
         *
         * @return The name of the {@link Roofing}.
         */
        @Override public String getName()
        {
            return null;
        }

        /**
         * Returns the description of the {@link Roofing}.
         *
         * @return The description of the {@link Roofing}.
         */
        @Override public String getDescription()
        {
            return null;
        }

        /**
         * Returns the minimum slope the {@link Roofing} must have.
         *
         * @return The minimum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
         */
        @Override public int getMinimumSlope()
        {
            return 0;
        }

        /**
         * Returns the maximum slope the {@link Roofing} must have.
         *
         * @return The maximum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
         */
        @Override public int getMaximumSlope()
        {
            return 0;
        }

        /**
         * Returns the price of the {@link Roofing} per square meter (in øre).
         *
         * @return The price of the {@link Roofing} per square meter (in øre).
         */
        @Override public int getPricePerSquareMeter()
        {
            return 0;
        }
    }
}
