package tvestergaard.fog.data.contraints;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OffsetConstraintTest
{

    @Test
    public void getOffset() throws Exception
    {
        for (int x = 0; x < 10; x++) {
            OffsetConstraint selector = new OffsetConstraint(x);
            assertEquals(x, selector.offset);
            assertEquals(x, selector.getOffset());
        }
    }
}