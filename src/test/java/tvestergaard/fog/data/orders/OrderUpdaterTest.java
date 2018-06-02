package tvestergaard.fog.data.orders;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static tvestergaard.fog.Helpers.*;

public class OrderUpdaterTest
{
    @Test
    public void from() throws Exception
    {

        int         expectedId       = randomInt();
        int         expectedCustomer = randomInt();
        int         expectedWidth    = randomInt();
        int         expectedLength   = randomInt();
        int         expectedHeight   = randomInt();
        int         expectedRoofing  = randomInt();
        int         expectedSlope    = randomInt();
        boolean     expectedActive   = randomBoolean();
        ShedUpdater expectedShed     = mock(ShedUpdater.class);
        String      expectedComment  = randomString();

        OrderUpdater instance = OrderUpdater.from(expectedId, expectedCustomer, expectedWidth, expectedLength,
                expectedHeight, expectedRoofing, expectedSlope, expectedActive, expectedShed, expectedComment);

        assertEquals(expectedId, instance.getId());
        assertEquals(expectedCustomer, instance.getCustomerId());
        assertEquals(expectedWidth, instance.getWidth());
        assertEquals(expectedLength, instance.getLength());
        assertEquals(expectedHeight, instance.getHeight());
        assertEquals(expectedRoofing, instance.getRoofingId());
        assertEquals(expectedSlope, instance.getSlope());
        assertEquals(expectedActive, instance.isActive());
        assertEquals(expectedShed, instance.getShedUpdater());
        assertEquals(expectedShed, instance.getShedBlueprint());
        assertEquals(expectedComment, instance.getComment());
    }
}