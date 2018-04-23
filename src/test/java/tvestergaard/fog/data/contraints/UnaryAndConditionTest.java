package tvestergaard.fog.data.contraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class UnaryAndConditionTest
{

    @Test
    public void getOperand() throws Exception
    {
        UnaryAndCondition operand   = new UnaryAndCondition(null);
        UnaryAndCondition condition = new UnaryAndCondition(operand);

        assertSame(operand, condition.operand);
        assertSame(operand, condition.getOperand());
    }
}