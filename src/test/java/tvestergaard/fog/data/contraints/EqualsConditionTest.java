package tvestergaard.fog.data.contraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class EqualsConditionTest
{

    @Test
    public void getColumn() throws Exception
    {
        String          column    = "some_string";
        EqualsCondition condition = new EqualsCondition(column, null);
        assertSame(column, condition.column);
        assertSame(column, condition.getColumn());
    }

    @Test
    public void getValue() throws Exception
    {
        String          value     = "some_string";
        EqualsCondition condition = new EqualsCondition(null, value);
        assertSame(value, condition.value);
        assertSame(value, condition.getValue());
    }
}