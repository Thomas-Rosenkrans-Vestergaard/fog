package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class BinaryAndConditionTest
{

    @Test
    public void getLeft() throws Exception
    {
        BinaryAndCondition left      = new BinaryAndCondition(null, null);
        BinaryAndCondition condition = new BinaryAndCondition(left, null);

        assertSame(left, condition.left);
        assertSame(left, condition.getLeft());
    }

    @Test
    public void getRight() throws Exception
    {
        BinaryAndCondition right     = new BinaryAndCondition(null, null);
        BinaryAndCondition condition = new BinaryAndCondition(null, right);

        assertSame(right, condition.right);
        assertSame(right, condition.getRight());
    }
}