package tvestergaard.fog.data.purchases;

import org.junit.Test;
import tvestergaard.fog.data.offers.Offer;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static tvestergaard.fog.Helpers.randomInt;

public class PurchaseRecordTest
{

    @Test
    public void getId() throws Exception
    {
        int            expected = randomInt(0);
        PurchaseRecord instance = new PurchaseRecord(expected, -1, null, -1, null);
        assertEquals(expected, instance.getId());
    }

    @Test
    public void getOfferId() throws Exception
    {
        int            expected = randomInt(0);
        PurchaseRecord instance = new PurchaseRecord(-1, expected, null, -1, null);
        assertEquals(expected, instance.getOfferId());
    }

    @Test
    public void getOffer() throws Exception
    {
        Offer          offer    = mock(Offer.class);
        PurchaseRecord instance = new PurchaseRecord(-1, -1, offer, -1, null);
        assertEquals(offer, instance.getOffer());
    }

    @Test
    public void getBomId() throws Exception
    {
        int            expected = randomInt(0);
        PurchaseRecord instance = new PurchaseRecord(-1, -1, null, expected, null);
        assertEquals(expected, instance.getBomId());
    }

    @Test
    public void getCreatedAt() throws Exception
    {
        LocalDateTime  expected = LocalDateTime.now();
        PurchaseRecord instance = new PurchaseRecord(-1, -1, null, -1, expected);
        assertEquals(expected, instance.getCreatedAt());
    }
}