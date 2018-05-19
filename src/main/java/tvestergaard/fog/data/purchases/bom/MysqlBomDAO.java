package tvestergaard.fog.data.purchases.bom;

import com.mysql.cj.jdbc.MysqlDataSource;
import tvestergaard.fog.data.AbstractMysqlDAO;
import tvestergaard.fog.data.MysqlDataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlBomDAO extends AbstractMysqlDAO implements BomDAO
{

    /**
     * Creates a new {@link MysqlBomDAO}.
     *
     * @param source The source used when accessing the data.
     */
    public MysqlBomDAO(MysqlDataSource source)
    {
        super(source);
    }

    /**
     * Returns the bom with the provided id.
     *
     * @param id The id of the bom to retrieve.
     * @return The bom instance representing the retrieved bom. {@code null} in case no bom with the provided id exists.
     * @throws MysqlDataAccessException When a data storage exception occurs while performing the operation.
     */
    @Override public Bom get(int id) throws MysqlDataAccessException
    {

        try {

            Connection connection = getConnection();

            String bomSQL = "SELECT * FROM bom WHERE id = ?";
            try (PreparedStatement bomStatement = connection.prepareStatement(bomSQL)) {
                bomStatement.setInt(1, id);
                ResultSet bom = bomStatement.executeQuery();
                if (!bom.first())
                    return null;

                List<BomLine> lines = new ArrayList();
                String linesSQL = "SELECT * FROM bom_lines bl " +
                        "INNER JOIN materials ON bl.material = materials.id " +
                        "INNER JOIN categories ON categories.id = materials.category WHERE bom = ?";
                try (PreparedStatement linesStatement = connection.prepareStatement(linesSQL)) {
                    linesStatement.setInt(1, id);
                    ResultSet linesResults = linesStatement.executeQuery();
                    while (linesResults.next())
                        lines.add(createBomLine(linesResults, "bl", "materials", "categories"));

                    List<BomDrawing> drawings    = new ArrayList<>();
                    String           drawingsSQL = "SELECT * FROM bom_drawings bd WHERE bom = ?";
                    try (PreparedStatement drawingsStatement = connection.prepareStatement(drawingsSQL)) {
                        drawingsStatement.setInt(1, id);
                        ResultSet drawingResults = drawingsStatement.executeQuery();
                        while (drawingResults.next())
                            drawings.add(createBomDrawing(drawingResults, "bd"));

                        return new BomRecord(bom.getInt("bom.id"), lines, lines, drawings, drawings);
                    }
                }
            }

        } catch (SQLException e) {
            throw new MysqlDataAccessException(e);
        }
    }
}
