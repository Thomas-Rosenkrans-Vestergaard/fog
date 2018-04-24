package tvestergaard.fog.data.constraints;

import java.util.EnumSet;

public interface Record<T extends Enum<T>>
{

    EnumSet<T> getColumns();
}
