package tvestergaard.fog.data.constraints;

public class InCondition<C extends Column<C>> implements WhereCondition<C>
{

    private final C        column;
    private final Object[] arguments;

    public InCondition(C column, Object[] arguments)
    {
        this.column = column;
        this.arguments = arguments;
    }

    public C getColumn()
    {
        return this.column;
    }

    public Object[] getArguments()
    {
        return arguments;
    }
}
