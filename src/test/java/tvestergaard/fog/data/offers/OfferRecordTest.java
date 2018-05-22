package tvestergaard.fog.data.offers;

import org.junit.Test;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.orders.Order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static tvestergaard.fog.Helpers.randomInt;

public class OfferRecordTest
{

    @Test
    public void getId() throws Exception
    {

        int         expected = randomInt();
        OfferRecord instance = new OfferRecord(expected, null, -1, null, -1, -1, null, null);
        assertEquals(expected, instance.getId());
    }

    @Test
    public void getOrderId() throws Exception
    {
        int         expected = randomInt();
        OfferRecord instance = new OfferRecord(-1, null, expected, null, -1, -1, null, null);
        assertEquals(expected, instance.getOrderId());
    }

    @Test
    public void getEmployeeId() throws Exception
    {
        int         expected = randomInt();
        OfferRecord instance = new OfferRecord(-1, null, -1, null, expected, -1, null, null);
        assertEquals(expected, instance.getEmployeeId());
    }

    @Test
    public void getPrice() throws Exception
    {
        int         expected = randomInt();
        OfferRecord instance = new OfferRecord(-1, null, -1, null, -1, expected, null, null);
        assertEquals(expected, instance.getPrice());
    }

    @Test
    public void getOrder() throws Exception
    {
        Order       order    = mock(Order.class);
        OfferRecord instance = new OfferRecord(-1, order, -1, null, -1, -1, null, null);
        assertSame(order, instance.getOrder());
    }

    @Test
    public void getEmployee() throws Exception
    {
        Employee    employee = mock(Employee.class);
        OfferRecord instance = new OfferRecord(-1, null, -1, employee, -1, -1, null, null);
        assertSame(employee, instance.getEmployee());
    }

    @Test
    public void getStatus() throws Exception
    {

    }

    @Test
    public void getCreatedAt() throws Exception
    {

    }
}