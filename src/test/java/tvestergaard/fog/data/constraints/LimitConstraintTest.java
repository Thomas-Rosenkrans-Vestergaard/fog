package tvestergaard.fog.data.constraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LimitConstraintTest
{

    @Test
    public void getLimit() throws Exception
    {
        for (int x = 0; x < 10; x++) {
            LimitConstraint<TestEnum> selector = new LimitConstraint(x);
            assertEquals(x, selector.limit);
            assertEquals(x, selector.getLimit());
        }
    }
}