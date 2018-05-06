package tvestergaard.fog.data.roofing;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.MysqlDataAccessException;
import tvestergaard.fog.data.constraints.Constraint;
import tvestergaard.fog.data.constraints.StatementBinder;
import tvestergaard.fog.data.constraints.StatementGenerator;
import tvestergaard.fog.data.materials.SimpleMaterial;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.append;
import static tvestergaard.fog.data.constraints.Constraint.limit;

public class MysqlRoofingDAO extends AbstractMysqlDAO implements RoofingDAO
{

    /**
     * The generator used to generate SQL for the constraints provided to this DAO.
     */
    private final StatementGenerator<RoofingColumn> generator = new StatementGenerator();

    /**
     * The binder used to bind prepared variables for the constraints provided to this DAO.
     */
    private final StatementBinder<RoofingColumn> binder = new StatementBinder();

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
     * Returns the roofings in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting roofings.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override
    public List<Roofing> get(Constraint<RoofingColumn>... constraints) throws MysqlDataAccessException
    {
        final List<Roofing> roofings = new ArrayList<>();
        final String        SQL      = generator.generate("SELECT * FROM roofings", constraints);
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            binder.bind(statement, constraints);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                roofings.add(createRoofing("roofings", resultSet));

            return roofings;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the first roofing matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first roofing matching the provided constraints. Returns {@code null} when no constraints matches the
     * provided constraints.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override
    public Roofing first(Constraint<RoofingColumn>... constraints) throws MysqlDataAccessException
    {
        List<Roofing> roofings = get(append(constraints, limit(1)));

        return roofings.isEmpty() ? null : roofings.get(0);
    }

    /**
     * Inserts a new roofing into the data storage.
     *
     * @param blueprint The roofing blueprint that contains the information necessary to create the roofing.
     * @return The roofing instance representing the newly created roofing.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Roofing create(RoofingBlueprint blueprint) throws MysqlDataAccessException
    {
        try {
            final String SQL = "INSERT INTO roofings (`name`, description, active, `type`) " +
                    "VALUES (?, ?, ?, ?)";
            Connection connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, blueprint.getName());
                statement.setString(2, blueprint.getDescription());
                statement.setBoolean(3, blueprint.isActive());
                statement.setString(4, blueprint.getType().name());
                int updated = statement.executeUpdate();
                connection.commit();
                if (updated == 0)
                    return null;
                ResultSet generated = statement.getGeneratedKeys();
                generated.first();
                return new RoofingRecord(generated.getInt(1), blueprint.getName(), blueprint.getDescription(),
                        blueprint.isActive(), blueprint.getType());
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Updates the entity in the data storage to match the provided {@code roofing}.
     *
     * @param updater The roofing updater that contains the information necessary to create the roofing.
     * @return {@link true} if the record was updated.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public boolean update(RoofingUpdater updater) throws MysqlDataAccessException
    {
        try {
            final String SQL        = "UPDATE roofings SET name = ?, description = ?, active = ?, `type` = ? WHERE id = ?";
            Connection   connection = getConnection();
            try (PreparedStatement statement = connection.prepareStatement(SQL)) {
                statement.setString(1, updater.getName());
                statement.setString(2, updater.getDescription());
                statement.setBoolean(3, updater.isActive());
                statement.setString(4, updater.getType().name());
                statement.setInt(5, updater.getId());
                int updated = statement.executeUpdate();
                connection.commit();
                return updated != 0;
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the components definitions for the provided roofing type.
     *
     * @param roofingType The id of the roofing to return the components of.
     * @return The components for the roofing with the provided id.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Set<RoofingComponentDefinition> getComponentsFor(RoofingType roofingType) throws DataAccessException
    {
        Set<RoofingComponentDefinition> definitions = new HashSet<>();

        String SQL = "SELECT * FROM roofing_component_definitions rcd " +
                "INNER JOIN categories ON rcd.category = categories.id WHERE rcd.roofing_type = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setString(1, roofingType.name());
            ResultSet componentResults = statement.executeQuery();
            String materialSQL = "SELECT * FROM materials " +
                    "INNER JOIN categories ON materials.category = categories.id WHERE materials.category = ?";
            try (PreparedStatement materialsStatement = getConnection().prepareStatement(materialSQL)) {
                while (componentResults.next()) {
                    materialsStatement.setInt(1, componentResults.getInt("rcd.category"));
                    ResultSet materials = materialsStatement.executeQuery();
                    definitions.add(createRoofingDefinition("rcd", componentResults, "categories", "materials", materials));
                }
            }

            return definitions;
        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the components active for the roofing with the provided id.
     *
     * @param roofing The id of the roofing to return the active components of.
     * @return The list of the components active for the roofing with the provided id.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public List<RoofingComponentValue> getComponentsFor(int roofing) throws MysqlDataAccessException
    {
        List<RoofingComponentValue> components = new ArrayList<>();

        String SQL = "SELECT * FROM roofing_component_values rcv " +
                "INNER JOIN roofing_component_definitions rcd ON rcv.component = rcd.id " +
                "INNER JOIN materials ON rcv.material = materials.id " +
                "INNER JOIN categories ON materials.category = categories.id " +
                "WHERE rcv.roofing = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            statement.setInt(1, roofing);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                components.add(createRoofingComponent(resultSet, "rcv", "rcd", "materials", "categories"));

            return components;

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    /**
     * Returns the material choices for the provided components.
     *
     * @param components The component(s) to return the values of.
     * @return Contains the material choices for the provided components. The material choices for some component id
     * is mapped to the component id.
     * @throws DataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Multimap<Integer, SimpleMaterial> getMaterialChoicesForComponents(int... components) throws DataAccessException
    {
        Multimap<Integer, SimpleMaterial> results = ArrayListMultimap.create();

        String SQL = "SELECT * FROM roofing_component_values rcv " +
                "INNER JOIN materials ON rcv.material = materials.id " +
                "INNER JOIN categories ON materials.category = categories.id " +
                "WHERE rcv.component IN (" + createIn(components.length) + ")";
        try (PreparedStatement statement = getConnection().prepareStatement(SQL)) {
            for (int i = 0; i < components.length; i++)
                statement.setInt(i + 1, components[i]);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                results.put(
                        resultSet.getInt("rcv.component"),
                        createSimpleMaterial("materials", "categories", resultSet));

            return results;

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }

    private String createIn(int size)
    {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < size; x++) {
            if (x != 0) builder.append(',');
            builder.append('?');
        }

        return builder.toString();
    }
}
