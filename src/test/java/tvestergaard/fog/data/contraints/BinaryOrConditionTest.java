package tvestergaard.fog.data.contraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class BinaryOrConditionTest
{

    @Test
    public void getLeft() throws Exception
    {
        BinaryOrCondition left      = new BinaryOrCondition(null, null);
        BinaryOrCondition condition = new BinaryOrCondition(left, null);

        assertSame(left, condition.left);
        assertSame(left, condition.getLeft());
    }

    @Test
    public void getRight() throws Exception
    {
        BinaryOrCondition right     = new BinaryOrCondition(null, null);
        BinaryOrCondition condition = new BinaryOrCondition(null, right);

        assertSame(right, condition.right);
        assertSame(right, condition.getRight());
    }
}