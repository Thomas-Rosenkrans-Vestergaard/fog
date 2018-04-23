package tvestergaard.fog.data.contraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.data.contraints.Constraint.*;

public class SQLGeneratorTest
{

    private SQLGenerator generator = new SQLGenerator();

    @Test
    public void generateSQLWhereEq() throws Exception
    {
        String expected = "WHERE `column_name` = ?";
        String actual   = generator.generateSQL(where(eq("column_name", "")));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateSQLWhereLike() throws Exception
    {
        String expected = "WHERE `column_name` LIKE ?";
        String actual   = generator.generateSQL(where(like("column_name", "")));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateSQLWhereEqAndLike() throws Exception
    {
        String expected = "WHERE (`column_name` = ? AND `column_name` LIKE ?)";
        String actual   = generator.generateSQL(where(and(eq("column_name", ""), like("column_name", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateSQLWhereEqOrLike() throws Exception
    {
        String expected = "WHERE (`column_name` = ? OR `column_name` LIKE ?)";
        String actual   = generator.generateSQL(where(or(eq("column_name", ""), like("column_name", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateSQLWhereEqAndLikeUnary() throws Exception
    {
        String expected = "WHERE `column_name` = ? AND `column_name` LIKE ?";
        String actual   = generator.generateSQL(where(eq("column_name", ""), and(like("column_name", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateWhereEqOrLikeUnary() throws Exception
    {
        String expected = "WHERE `column_name` = ? OR `column_name` LIKE ?";
        String actual   = generator.generateSQL(where(eq("column_name", ""), or(like("column_name", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateOrderDESC() throws Exception
    {
        String expected = "ORDER BY `column_name` DESC";
        String actual   = generator.generateSQL(order("column_name", desc()));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateOrderACS() throws Exception
    {
        String expected = "ORDER BY `column_name` ASC";
        String actual   = generator.generateSQL(order("column_name", asc()));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateLimit() throws Exception
    {
        String expected = "LIMIT ?";
        String actual   = generator.generateSQL(limit(5));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateOffset() throws Exception
    {
        String expected = "OFFSET ?";
        String actual   = generator.generateSQL(offset(1));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateLimitOffset() throws Exception
    {
        String expected = "LIMIT?OFFSET?";
        String actual   = generator.generateSQL(limit(10), offset(20));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateOrdering() throws Exception
    {
        String expected = "WHERE `column_name` = ? ORDER BY `column_name` DESC LIMIT ? OFFSET ?";
        String actual = generator.generateSQL(
                where(eq("column_name", "")),
                order("column_name", desc()),
                limit(1),
                offset(5));

        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void setParameters() throws Exception
    {

    }

    private String clean(String str)
    {
        return str.replaceAll("\\s+", "");
    }
}