package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderConstraintTest
{
    @Test
    public void getColumn() throws Exception
    {
        OrderConstraint<TestEnum> constraint = new OrderConstraint(TestEnum.COLUMN_THREE, null);
        assertEquals(TestEnum.COLUMN_THREE, constraint.getColumn());
    }

    @Test
    public void getDirection() throws Exception
    {
        OrderConstraint<TestEnum> constraint = new OrderConstraint(null, OrderConstraint.Direction.DESC);
        assertEquals(OrderConstraint.Direction.DESC, constraint.getDirection());
    }
}