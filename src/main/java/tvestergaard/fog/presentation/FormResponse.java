package tvestergaard.fog.presentation;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.HashMap;

public class FormResponse
{

    /**
     * The values that were sent mapped to the identifier of the field.
     */
    private final HashMap<String, Object> values = new HashMap<>();

    /**
     * Error messages for the fields.
     */
    private final Multimap<String, String> errors = ArrayListMultimap.create();

    public void setValue(String field, Object value)
    {
        this.values.put(field, value);
    }

    public Object getValue(String field)
    {
        return this.values.get(field);
    }

    public Collection<String> getErrors(String field)
    {
        return errors.get(field);
    }

    public void addError(String field, String error)
    {
        errors.put(field, error);
    }

    public void clear()
    {
        this.values.clear();
        this.errors.clear();
    }
}
