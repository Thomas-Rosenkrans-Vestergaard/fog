package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.presentation.tables.ColumnDefinition;
import tvestergaard.fog.presentation.tables.HTML;
import tvestergaard.fog.presentation.tables.Table;
import tvestergaard.fog.presentation.tables.TableColumn;

import java.util.ArrayList;
import java.util.List;

public class OrdersTable implements Table<Order>
{
    private List<TableColumn<Order>> columns = new ArrayList<>();

    public OrdersTable()
    {
        this.columns = new ArrayList<>();
        this.columns.add(ColumnDefinition.<Order>of("ID", Order::getId));
        this.columns.add(ColumnDefinition.<Order>of("Kunde", order -> HTML.link("customers?id=" + order.getCustomer().getId(), order.getCustomer().getName())));
        this.columns.add(ColumnDefinition.<Order>of("Type", order -> order.getType().getDisplayName()));
        this.columns.add(ColumnDefinition.<Order>of("Bekædning", order -> HTML.link("claddings?id=" + order.getCladding().getId(), order.getCladding().getName())));
        this.columns.add(ColumnDefinition.<Order>of("Bredde", Order::getWidth));
        this.columns.add(ColumnDefinition.<Order>of("Længde", Order::getLength));
        this.columns.add(ColumnDefinition.<Order>of("Height", Order::getHeight));
        this.columns.add(ColumnDefinition.<Order>of("Tag", order -> HTML.link("roofings?id=" + order.getRoofing().getId(), order.getRoofing().getName())));
        this.columns.add(ColumnDefinition.<Order>of("Slope", Order::getSlope));
        this.columns.add(ColumnDefinition.<Order>of("Spær", order -> order.getRaftersConstruction().getDisplayName()));
        this.columns.add(ColumnDefinition.<Order>of("Redskabsskur", order -> HTML.link("sheds?id=" + order.getShed().getId(), Integer.toString(order.getShed().getId()))));
    }

    @Override public List<TableColumn<Order>> getColumns()
    {
        return this.columns;
    }
}
