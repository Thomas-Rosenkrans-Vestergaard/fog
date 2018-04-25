package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class EqualsConditionTest
{

    @Test
    public void getColumn() throws Exception
    {
        EqualsCondition<TestEnum> condition = new EqualsCondition(TestEnum.COLUMN_ONE, null);
        assertSame(TestEnum.COLUMN_ONE, condition.column);
        assertSame(TestEnum.COLUMN_ONE, condition.getColumn());
    }

    @Test
    public void getValue() throws Exception
    {
        EqualsCondition<TestEnum> condition = new EqualsCondition(null, TestEnum.COLUMN_THREE);
        assertSame(TestEnum.COLUMN_THREE, condition.value);
        assertSame(TestEnum.COLUMN_THREE, condition.getValue());
    }
}