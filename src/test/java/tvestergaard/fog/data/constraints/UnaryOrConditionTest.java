package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class UnaryOrConditionTest
{

    @Test
    public void getOperand() throws Exception
    {
        UnaryOrCondition operand   = new UnaryOrCondition(null);
        UnaryOrCondition condition = new UnaryOrCondition(operand);

        assertSame(operand, condition.operand);
        assertSame(operand, condition.getOperand());
    }
}