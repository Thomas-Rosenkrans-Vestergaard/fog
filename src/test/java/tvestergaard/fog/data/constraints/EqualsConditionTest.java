package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class EqualsConditionTest
{

    @Test
    public void getColumn() throws Exception
    {
        EqualsCondition<TestColumn> condition = new EqualsCondition(TestColumn.COLUMN_ONE, null);
        assertSame(TestColumn.COLUMN_ONE, condition.column);
        assertSame(TestColumn.COLUMN_ONE, condition.getColumn());
    }

    @Test
    public void getValue() throws Exception
    {
        EqualsCondition<TestColumn> condition = new EqualsCondition(null, TestColumn.COLUMN_THREE);
        assertSame(TestColumn.COLUMN_THREE, condition.value);
        assertSame(TestColumn.COLUMN_THREE, condition.getValue());
    }
}