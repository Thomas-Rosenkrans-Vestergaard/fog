package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertSame;

public class BinaryAndConditionTest
{

    @Test
    public void getLeft() throws Exception
    {
        BinaryAndCondition<TestEnum> left      = new BinaryAndCondition<>(null, null);
        BinaryAndCondition<TestEnum> condition = new BinaryAndCondition<>(left, null);

        assertSame(left, condition.left);
        assertSame(left, condition.getLeft());
    }

    @Test
    public void getRight() throws Exception
    {
        BinaryAndCondition<TestEnum> right     = new BinaryAndCondition(null, null);
        BinaryAndCondition<TestEnum> condition = new BinaryAndCondition(null, right);

        assertSame(right, condition.right);
        assertSame(right, condition.getRight());
    }
}