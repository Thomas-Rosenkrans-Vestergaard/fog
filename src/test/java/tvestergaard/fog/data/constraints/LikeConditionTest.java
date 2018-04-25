package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class LikeConditionTest
{

    @Test
    public void getColumn() throws Exception
    {
        LikeCondition<TestEnum> condition = new LikeCondition(TestEnum.COLUMN_TWO, null);

        assertSame(TestEnum.COLUMN_TWO, condition.column);
        assertSame(TestEnum.COLUMN_TWO, condition.getColumn());
    }

    @Test
    public void getOperand() throws Exception
    {
        String                  operand   = "some_string_like";
        LikeCondition<TestEnum> condition = new LikeCondition(TestEnum.COLUMN_TWO, operand);

        assertEquals(operand, condition.operand);
        assertEquals(operand, condition.getOperand());
    }
}