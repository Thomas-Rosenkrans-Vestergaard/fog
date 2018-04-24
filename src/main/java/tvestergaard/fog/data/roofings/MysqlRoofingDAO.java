package tvestergaard.fog.data.roofings;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlRoofingDAO extends AbstractMysqlDAO implements RoofingDAO
{

    /**
     * Creates a new {@link MysqlRoofingDAO}.
     *
     * @param source The {@link MysqlDataSource} that provides the connection used by the {@link MysqlRoofingDAO}.
     */
    public MysqlRoofingDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns a complete list of the {@link Roofing}s in the system.
     *
     * @return The complete list of the {@link Roofing}s in the system.
     * @throws MysqlDataAccessException When an exception occurs while performing the operation.
     */
    @Override public List<Roofing> get() throws MysqlDataAccessException
    {
        try {
            final List<Roofing> roofings = new ArrayList<>();
            final String        SQL      = "SELECT * FROM roofings";
            try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
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

    /**
     * The implementation of {@link Roofing} returned by the {@link MysqlRoofingDAO}.
     */
    private class MysqlRoofing implements Roofing
    {

        /**
         * The unique identifier of the {@link Roofing}.
         */
        private final int id;

        /**
         * The name of the {@link Roofing}.
         */
        private String name;

        /**
         * The description of the {@link Roofing}.
         */
        private String description;

        /**
         * The minimum slope the {@link Roofing} must have.
         */
        private int minimumSlope;

        /**
         * The maximum slope the {@link Roofing} must have.
         */
        private int maximumSlope;

        /**
         * The price of the {@link Roofing} per square meter (in øre).
         */
        private int pricePerSquareMeter;

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
            return id;
        }

        /**
         * Returns the name of the {@link Roofing}.
         *
         * @return The name of the {@link Roofing}.
         */
        @Override public String getName()
        {
            return name;
        }

        /**
         * Sets the name of the {@link Roofing}.
         *
         * @param name The new name.
         */
        @Override public void setName(String name)
        {
            this.name = name;
        }

        /**
         * Returns the description of the {@link Roofing}.
         *
         * @return The description of the {@link Roofing}.
         */
        @Override public String getDescription()
        {
            return description;
        }

        /**
         * Sets the description of the {@link Roofing}.
         *
         * @param description The new description.
         */
        @Override public void setDescription(String description)
        {
            this.description = description;
        }

        /**
         * Returns the minimum slope the {@link Roofing} must have.
         *
         * @return The minimum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
         */
        @Override public int getMinimumSlope()
        {
            return minimumSlope;
        }

        /**
         * Sets the minimum slope the {@link Roofing} must have.
         *
         * @param minimumSlope The new minimum slope. Range between 0 and 90 (exclusive).
         */
        @Override public void setMinimumSlope(int minimumSlope)
        {
            this.minimumSlope = minimumSlope;
        }

        /**
         * Returns the maximum slope the {@link Roofing} must have.
         *
         * @return The maximum slope the {@link Roofing} must have. Returns an integer between 0 and 90 (exclusive).
         */
        @Override public int getMaximumSlope()
        {
            return maximumSlope;
        }

        /**
         * Sets the maximum slope the {@link Roofing} can have.
         *
         * @param maximumSlope The new maximum slope. Range between 0 and 90 (exclusive).
         */
        @Override public void setMaximumSlope(int maximumSlope)
        {
            this.maximumSlope = maximumSlope;
        }

        /**
         * Returns the price of the {@link Roofing} per square meter (in øre).
         *
         * @return The price of the {@link Roofing} per square meter (in øre).
         */
        @Override public int getPricePerSquareMeter()
        {
            return pricePerSquareMeter;
        }

        /**
         * Sets the price of the {@link Roofing} per square meter.
         *
         * @param price The new price (in øre).
         */
        @Override public void setPricePerSquareMeter(int price)
        {
            this.pricePerSquareMeter = price;
        }

        @Override public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MysqlRoofing that = (MysqlRoofing) o;

            return id == that.id;
        }

        @Override public int hashCode()
        {
            return id;
        }
    }
}
