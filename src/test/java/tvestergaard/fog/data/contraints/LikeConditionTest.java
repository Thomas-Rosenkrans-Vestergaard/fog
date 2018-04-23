package tvestergaard.fog.data.contraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class LikeConditionTest
{

    @Test
    public void getColumn() throws Exception
    {
        String        column    = "some_string_like";
        LikeCondition condition = new LikeCondition(column, null);

        assertSame(column, condition.column);
        assertSame(column, condition.getColumn());
    }

    @Test
    public void getOperand() throws Exception
    {
        String        operand   = "some_string_like";
        LikeCondition condition = new LikeCondition(null, operand);

        assertSame(operand, condition.operand);
        assertSame(operand, condition.getOperand());
    }
}