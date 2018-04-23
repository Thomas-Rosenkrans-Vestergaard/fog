package tvestergaard.fog.data.contraints;

import org.junit.Test;

import static org.junit.Assert.*;

public class OrderConstraintTest
{
    @Test
    public void getColumn() throws Exception
    {
        OrderConstraint selector = new OrderConstraint(null, OrderConstraint.Direction.ASC);
        assertEquals(OrderConstraint.Direction.ASC, selector.getDirection());
    }

    @Test
    public void getDirection() throws Exception
    {
    }
}