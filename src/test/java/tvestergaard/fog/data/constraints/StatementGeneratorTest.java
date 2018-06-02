package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static tvestergaard.fog.data.constraints.Constraint.*;
import static tvestergaard.fog.data.constraints.TestColumn.*;

public class StatementGeneratorTest
{

    private StatementGenerator<TestColumn> generator = new StatementGenerator();

    @Test
    public void whereEquals() throws Exception
    {
        String expected = "WHERE `column_one` = ?";
        String actual   = generator.generate(where(eq(COLUMN_ONE, "")));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void havingEquals() throws Exception
    {
        String expected = "HAVING `column_one` = ?";
        String actual   = generator.generate(having(eq(COLUMN_ONE, "")));
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
    public void havingLike() throws Exception
    {
        String expected = "HAVING `column_two` LIKE ?";
        String actual   = generator.generate(having(like(COLUMN_TWO, "")));
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
    public void havingEqualsAndLike() throws Exception
    {
        String expected = "HAVING (`column_one` = ? AND `column_two` LIKE ?)";
        String actual   = generator.generate(having(and(eq(COLUMN_ONE, ""), like(COLUMN_TWO, ""))));
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
    public void havingEqualsOrLike() throws Exception
    {
        String expected = "HAVING (`column_two` = ? OR `column_three` LIKE ?)";
        String actual = generator.generate(
                having(or(eq(COLUMN_TWO, ""), like(COLUMN_THREE, ""))));
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
    public void havingEqualsAndUnaryLike() throws Exception
    {
        String expected = "HAVING `column_one` = ? AND `column_three` LIKE ?";
        String actual = generator.generate(
                having(eq(COLUMN_ONE, ""), and(like(COLUMN_THREE, ""))));
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
    public void havingEqualsOrUnaryLike() throws Exception
    {
        String expected = "HAVING `column_one` = ? OR `column_three` LIKE ?";
        String actual = generator.generate(
                having(eq(COLUMN_ONE, ""), or(like(COLUMN_THREE, ""))));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void orderDesc() throws Exception
    {
        String expected = "ORDER BY `column_two` DESC";
        String actual   = generator.generate(order(COLUMN_TWO, OrderDirection.DESC));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void orderAsc() throws Exception
    {
        String expected = "ORDER BY `column_three` ASC";
        String actual   = generator.generate(order(COLUMN_THREE, OrderDirection.ASC));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void groupBy() throws Exception
    {
        String expected = "GROUP BY `column_one`, `column_three`, `column_two`";
        String actual   = generator.generate(Constraint.groupBy(COLUMN_ONE, COLUMN_THREE, COLUMN_TWO));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void limit() throws Exception
    {
        String expected = "LIMIT ?";
        String actual   = generator.generate(Constraint.limit(TestColumn.class, 5));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void offset() throws Exception
    {
        String expected = "OFFSET ?";
        String actual   = generator.generate(Constraint.offset(TestColumn.class, 1));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateLimitOffset() throws Exception
    {
        String expected = "LIMIT?OFFSET?";
        String actual   = generator.generate(Constraint.limit(TestColumn.class, 10).offset(20));
        assertEquals(clean(expected), clean(actual));
    }

    @Test
    public void generateOrdering() throws Exception
    {
        String expected = "WHERE `column_one` = ? ORDER BY `column_two` DESC LIMIT ? OFFSET ?";
        String actual = generator.generate(
                order(COLUMN_TWO, OrderDirection.DESC).limit(1).where(eq(COLUMN_ONE, null)).offset(5));

        assertEquals(clean(expected), clean(actual));
    }

    private String clean(String str)
    {
        return str.replaceAll("\\s+", "");
    }
}