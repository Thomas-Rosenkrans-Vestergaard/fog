package tvestergaard.fog.data.contraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.data.contraints.Constraint.*;

public class StatementGeneratorTest
{

    private StatementGenerator generator = new StatementGenerator();

    @Test
    public void whereEquals() throws Exception
    {
        String expected = "WHERE `column` = ?";
        String actual   = generator.generate(where(eq("column", "")));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereLike() throws Exception
    {
        String expected = "WHERE `column` LIKE ?";
        String actual   = generator.generate(where(like("column", "")));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsAndLike() throws Exception
    {
        String expected = "WHERE (`column` = ? AND `column` LIKE ?)";
        String actual   = generator.generate(where(and(eq("column", ""), like("column", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsOrLike() throws Exception
    {
        String expected = "WHERE (`column` = ? OR `column` LIKE ?)";
        String actual = generator.generate(
                where(or(eq("column", ""), like("column", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsAndUnaryLike() throws Exception
    {
        String expected = "WHERE `column` = ? AND `column` LIKE ?";
        String actual = generator.generate(
                where(eq("column", ""), and(like("column", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsOrUnaryLike() throws Exception
    {
        String expected = "WHERE `column` = ? OR `column` LIKE ?";
        String actual = generator.generate(
                where(eq("column", ""), or(like("column", ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void orderDesc() throws Exception
    {
        String expected = "ORDER BY `column` DESC";
        String actual   = generator.generate(order("column", desc()));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void orderAsc() throws Exception
    {
        String expected = "ORDER BY `column` ASC";
        String actual   = generator.generate(order("column", asc()));
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
        String expected = "WHERE `column` = ? ORDER BY `column` DESC LIMIT ? OFFSET ?";
        String actual = generator.generate(
                where(eq("column", "")),
                order("column", desc()),
                Constraint.limit(1),
                Constraint.offset(5));

        assertEquals(clean(expected), clean(actual));
    }

    private String clean(String str)
    {
        return str.replaceAll("\\s+", "");
    }
}