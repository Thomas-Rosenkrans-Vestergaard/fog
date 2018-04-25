package tvestergaard.fog.logic;

import tvestergaard.fog.data.orders.OrderDAO;

public class OrderFacade
{

    private final OrderDAO dao;

    public OrderFacade(OrderDAO dao)
    {
        this.dao = dao;
    }


}
