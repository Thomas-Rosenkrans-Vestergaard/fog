package tvestergaard.fog.data.materials.attributes;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlAttributeDAO extends AbstractMysqlDAO implements AttributeDAO
{

    /**
     * Creates a new {@link MysqlAttributeDAO}.
     *
     * @param source The source from which to gather attributes.
     */
    public MysqlAttributeDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the attributes for the provided materials.
     *
     * @param materials The materials to return the attributes of.
     * @return The multimap containing the attributes mapped to their material.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Multimap<Integer, Attribute> get(int... materials) throws MysqlDataAccessException
    {
        Multimap<Integer, Attribute> result = ArrayListMultimap.create();

        try {

            Connection connection = getConnection();

            String SQL = "SELECT * FROM attribute_definitions ad " +
                    "INNER JOIN attribute_values av ON ad.id = av.attribute " +
                    "WHERE av.material IN (" + createArgumentList(materials.length) + ")";

            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                for (int x = 0; x < materials.length; x++)
                    statement.setInt(x + 1, materials[x]);
                ResultSet resultSet = statement.executeQuery();

                if (!resultSet.first())
                    return result;

                int currentMaterial = -1;
                while (resultSet.next()) {
                    if (currentMaterial != resultSet.getInt("av.material"))
                        currentMaterial = resultSet.getInt("av.material");

                    result.put(currentMaterial, createAttribute(resultSet, "ad", "av"));
                }
            }

            return result;

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
