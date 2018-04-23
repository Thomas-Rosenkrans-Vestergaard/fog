package tvestergaard.fog.data.contraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.data.contraints.Constraint.*;

public class PreparedStatementGeneratorTest
{

    private PreparedStatementGenerator generator = new PreparedStatementGenerator();

    @Test
    public void whereEquals() throws Exception
    {
        String expected = "WHERE `column_name` = ?";
        String actual   = generator.generate(where(eq("column_name", "")));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereLike() throws Exception
    {
        String expected = "WHERE `column_name` LIKE ?";
        String actual   = generator.generate(where(like("column_name", "")));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsAndLike() throws Exception
    {
        String expected = "WHERE (`column_name` = ? AND `column_name` LIKE ?)";
        String actual   = generator.generate(where(and(eq("column_name", ""), like("column_name", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsOrLike() throws Exception
    {
        String expected = "WHERE (`column_name` = ? OR `column_name` LIKE ?)";
        String actual = generator.generate(
                where(or(eq("column_name", ""), like("column_name", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsAndUnaryLike() throws Exception
    {
        String expected = "WHERE `column_name` = ? AND `column_name` LIKE ?";
        String actual = generator.generate(
                where(eq("column_name", ""), and(like("column_name", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsOrUnaryLike() throws Exception
    {
        String expected = "WHERE `column_name` = ? OR `column_name` LIKE ?";
        String actual = generator.generate(
                where(eq("column_name", ""), or(like("column_name", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void orderDesc() throws Exception
    {
        String expected = "ORDER BY `column_name` DESC";
        String actual   = generator.generate(order("column_name", desc()));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void orderAsc() throws Exception
    {
        String expected = "ORDER BY `column_name` ASC";
        String actual   = generator.generate(order("column_name", asc()));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void limit() throws Exception
    {
        String expected = "LIMIT ?";
        String actual   = generator.generate(Constraint.limit(5));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void offset() throws Exception
    {
        String expected = "OFFSET ?";
        String actual   = generator.generate(Constraint.offset(1));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateLimitOffset() throws Exception
    {
        String expected = "LIMIT?OFFSET?";
        String actual   = generator.generate(Constraint.limit(10), Constraint.offset(20));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateOrdering() throws Exception
    {
        String expected = "WHERE `column_name` = ? ORDER BY `column_name` DESC LIMIT ? OFFSET ?";
        String actual = generator.generate(
                where(eq("column_name", "")),
                order("column_name", desc()),
                Constraint.limit(1),
                Constraint.offset(5));

        assertEquals(clean(expected), clean(actual));
    }

    private String clean(String str)
    {
        return str.replaceAll("\\s+", "");
    }
}