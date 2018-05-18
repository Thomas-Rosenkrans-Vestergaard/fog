package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class BinaryAndConditionTest
{

    @Test
    public void getLeft() throws Exception
    {
        BinaryAndCondition<TestColumn> left      = new BinaryAndCondition<>(null, null);
        BinaryAndCondition<TestColumn> condition = new BinaryAndCondition<>(left, null);

        assertSame(left, condition.left);
        assertSame(left, condition.getLeft());
    }

    @Test
    public void getRight() throws Exception
    {
        BinaryAndCondition<TestColumn> right     = new BinaryAndCondition(null, null);
        BinaryAndCondition<TestColumn> condition = new BinaryAndCondition(null, right);

        assertSame(right, condition.right);
        assertSame(right, condition.getRight());
    }
}