package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class LikeConditionTest
{

    @Test
    public void getColumn() throws Exception
    {
        LikeCondition<TestColumn> condition = new LikeCondition(TestColumn.COLUMN_TWO, null);

        assertSame(TestColumn.COLUMN_TWO, condition.column);
        assertSame(TestColumn.COLUMN_TWO, condition.getColumn());
    }

    @Test
    public void getOperand() throws Exception
    {
        String                    operand   = "some_string_like";
        LikeCondition<TestColumn> condition = new LikeCondition(TestColumn.COLUMN_TWO, operand);

        assertEquals(operand, condition.operand);
        assertEquals(operand, condition.getOperand());
    }
}