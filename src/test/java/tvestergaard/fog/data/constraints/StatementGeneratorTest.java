package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.constraints.TestEnum.*;

public class StatementGeneratorTest
{

    private StatementGenerator<TestEnum> generator = new StatementGenerator();

    @Test
    public void whereEquals() throws Exception
    {
        String expected = "WHERE `column_one` = ?";
        String actual   = generator.generate(where(eq(COLUMN_ONE, "")));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereLike() throws Exception
    {
        String expected = "WHERE `column_two` LIKE ?";
        String actual   = generator.generate(where(like(COLUMN_TWO, "")));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsAndLike() throws Exception
    {
        String expected = "WHERE (`column_one` = ? AND `column_two` LIKE ?)";
        String actual   = generator.generate(where(and(eq(COLUMN_ONE, ""), like(COLUMN_TWO, ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsOrLike() throws Exception
    {
        String expected = "WHERE (`column_two` = ? OR `column_three` LIKE ?)";
        String actual = generator.generate(
                where(or(eq(COLUMN_TWO, ""), like(COLUMN_THREE, ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsAndUnaryLike() throws Exception
    {
        String expected = "WHERE `column_one` = ? AND `column_three` LIKE ?";
        String actual = generator.generate(
                where(eq(COLUMN_ONE, ""), and(like(COLUMN_THREE, ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void whereEqualsOrUnaryLike() throws Exception
    {
        String expected = "WHERE `column_one` = ? OR `column_three` LIKE ?";
        String actual = generator.generate(
                where(eq(COLUMN_ONE, ""), or(like(COLUMN_THREE, ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void orderDesc() throws Exception
    {
        String expected = "ORDER BY `column_two` DESC";
        String actual   = generator.generate(order(COLUMN_TWO, desc()));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void orderAsc() throws Exception
    {
        String expected = "ORDER BY `column_three` ASC";
        String actual   = generator.generate(order(COLUMN_THREE, asc()));
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
        String expected = "WHERE `column_one` = ? ORDER BY `column_two` DESC LIMIT ? OFFSET ?";
        String actual = generator.generate(
                order(COLUMN_TWO, desc()),
                Constraint.limit(1),
                where(eq(COLUMN_ONE, "")),
                Constraint.offset(5));

        assertEquals(clean(expected), clean(actual));
    }

    private String clean(String str)
    {
        return str.replaceAll("\\s+", "");
    }
}