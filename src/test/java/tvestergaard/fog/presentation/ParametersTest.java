package tvestergaard.fog.presentation;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;

import static org.junit.Assert.*;

public class ParametersTest
{
    private final Parameters parameters;


    public ParametersTest()
    {
        this.parameters = new Parameters(parameterName -> {

            if ("text".equals(parameterName))
                return "Some Text";

            if ("zero".equals(parameterName))
                return "0";

            if ("int-min".equals(parameterName))
                return "-2147483648";

            if ("int-max".equals(parameterName))
                return "+2147483647";

            if ("long-min".equals(parameterName))
                return "-9223372036854775808";

            if ("long-max".equals(parameterName))
                return "+9223372036854775807";

            if ("float-min".equals(parameterName))
                return "3.40282346638528860e-38";

            if ("float-max".equals(parameterName))
                return "3.40282346638528860e+38";

            if ("double-min".equals(parameterName))
                return "1.7976931348623157e-308";

            if ("double-max".equals(parameterName))
                return "1.7976931348623157e+308";

            if ("true".equals(parameterName))
                return "true";

            if ("false".equals(parameterName))
                return "false";

            if ("on".equals(parameterName))
                return "on";

            if ("off".equals(parameterName))
                return "off";

            if ("date-in-bounds".equals(parameterName))
                return "2001-01-01";

            if ("date-out-bounds".equals(parameterName))
                return "2000-18-23";

            if ("datetime-in-bounds".equals(parameterName))
                return "2007-12-03T10:15:30";

            if ("datetime-out-bounds".equals(parameterName))
                return "2007-12-03T10:99:30";

            if ("time-in-bounds".equals(parameterName))
                return "23:59";

            if ("time-out-bounds".equals(parameterName))
                return "23:60";

            if ("A".equals(parameterName))
                return "A";

            if ("B".equals(parameterName))
                return "B";

            if ("C".equals(parameterName))
                return "C";

            return null;
        });
    }

    @Test
    public void isPresent() throws Exception
    {
        assertTrue(parameters.isPresent("text"));
        assertTrue(parameters.isPresent("int-min"));
        assertFalse(parameters.isPresent("empty"));
    }

    @Test
    public void value() throws Exception
    {
        assertNull(parameters.value("empty"));
        assertEquals("Some Text", parameters.value("text"));
        assertEquals("-2147483648", parameters.value("int-min"));
    }

    @Test
    public void isInt() throws Exception
    {
        assertFalse(parameters.isInt("empty"));
        assertFalse(parameters.isInt("text"));

        assertTrue(parameters.isInt("int-min"));
        assertTrue(parameters.isInt("int-max"));
        assertTrue(parameters.isInt("zero"));
    }

    @Test
    public void getInt() throws Exception
    {
        assertEquals(0, parameters.getInt("zero"));
        assertEquals(Integer.MIN_VALUE, parameters.getInt("int-min"));
        assertEquals(Integer.MAX_VALUE, parameters.getInt("int-max"));
    }

    @Test
    public void isLong() throws Exception
    {
        assertFalse(parameters.isLong("empty"));
        assertFalse(parameters.isLong("text"));

        assertTrue(parameters.isLong("int-min"));
        assertTrue(parameters.isLong("int-max"));
        assertTrue(parameters.isLong("long-min"));
        assertTrue(parameters.isLong("long-max"));
        assertTrue(parameters.isLong("zero"));
    }

    @Test
    public void getLong() throws Exception
    {
        assertEquals(0, parameters.getLong("zero"));
        assertEquals(Integer.MIN_VALUE, parameters.getLong("int-min"));
        assertEquals(Integer.MAX_VALUE, parameters.getLong("int-max"));
        assertEquals(Long.MIN_VALUE, parameters.getLong("long-min"));
        assertEquals(Long.MAX_VALUE, parameters.getLong("long-max"));
    }

    @Test
    public void isFloat() throws Exception
    {
        assertFalse(parameters.isFloat("empty"));
        assertFalse(parameters.isFloat("text"));

        assertTrue(parameters.isFloat("int-min"));
        assertTrue(parameters.isFloat("int-max"));
        assertTrue(parameters.isFloat("long-min"));
        assertTrue(parameters.isFloat("long-max"));
        assertTrue(parameters.isFloat("float-min"));
        assertTrue(parameters.isFloat("float-max"));
    }

    @Test
    public void getFloat() throws Exception
    {
        assertEquals(Integer.MIN_VALUE, parameters.getFloat("int-min"), 1);
        assertEquals(Integer.MAX_VALUE, parameters.getFloat("int-max"), 1);
        assertEquals(Long.MIN_VALUE, parameters.getFloat("long-min"), 1);
        assertEquals(Long.MAX_VALUE, parameters.getFloat("long-max"), 1);
        assertEquals(Float.MIN_VALUE, parameters.getFloat("float-min"), 1);
        assertEquals(Float.MAX_VALUE, parameters.getFloat("float-max"), 1);
    }

    @Test
    public void isDouble() throws Exception
    {
        assertFalse(parameters.isDouble("empty"));
        assertFalse(parameters.isDouble("text"));

        assertTrue(parameters.isDouble("int-min"));
        assertTrue(parameters.isDouble("int-max"));
        assertTrue(parameters.isDouble("long-min"));
        assertTrue(parameters.isDouble("long-max"));
        assertTrue(parameters.isDouble("float-min"));
        assertTrue(parameters.isDouble("float-max"));
        assertTrue(parameters.isDouble("double-min"));
        assertTrue(parameters.isDouble("double-max"));
    }

    @Test
    public void getDouble() throws Exception
    {
        assertEquals(Integer.MIN_VALUE, parameters.getDouble("int-min"), 1);
        assertEquals(Integer.MAX_VALUE, parameters.getDouble("int-max"), 1);
        assertEquals(Long.MIN_VALUE, parameters.getDouble("long-min"), 1);
        assertEquals(Long.MAX_VALUE, parameters.getDouble("long-max"), 1);
        assertEquals(Float.MIN_VALUE, parameters.getDouble("float-min"), 1);
        assertEquals(Float.MAX_VALUE, parameters.getDouble("float-max"), 1);
        assertEquals(Double.MIN_VALUE, parameters.getDouble("double-min"), 1);
        assertEquals(Double.MAX_VALUE, parameters.getDouble("double-max"), 1);
    }

    @Test
    public void isBoolean() throws Exception
    {
        assertFalse(parameters.isBoolean("empty"));
        assertFalse(parameters.isBoolean("text"));

        assertTrue(parameters.isBoolean("true"));
        assertTrue(parameters.isBoolean("false"));
        assertTrue(parameters.isBoolean("on"));
        assertTrue(parameters.isBoolean("off"));
    }

    @Test
    public void getBoolean() throws Exception
    {
        assertTrue(parameters.getBoolean("true"));
        assertTrue(parameters.getBoolean("on"));

        assertFalse(parameters.getBoolean("false"));
        assertFalse(parameters.getBoolean("off"));
    }

    @Test
    public void isDate() throws Exception
    {
        assertFalse(parameters.isDate("empty"));
        assertFalse(parameters.isDate("text"));
        assertFalse(parameters.isDate("date-out-bounds"));

        assertTrue(parameters.isDate("date-in-bounds"));
    }

    @Test
    public void getDate() throws Exception
    {
        LocalDate date = parameters.getDate("date-in-bounds"); // 2001-01-01
        assertEquals(2001, date.getYear());
        assertEquals(1, date.getMonthValue());
        assertEquals(1, date.getDayOfMonth());
    }

    @Test
    public void isDatetime() throws Exception
    {
        assertFalse(parameters.isDatetime("empty"));
        assertFalse(parameters.isDatetime("text"));
        assertFalse(parameters.isDatetime("datetime-out-bounds"));

        assertTrue(parameters.isDatetime("datetime-in-bounds"));
    }

    @Test
    public void getDatetime() throws Exception
    {
        LocalDateTime dateTime = parameters.getDatetime("datetime-in-bounds"); // 2007-12-03T10:15:30

        assertEquals(2007, dateTime.getYear());
        assertEquals(12, dateTime.getMonthValue());
        assertEquals(3, dateTime.getDayOfMonth());
        assertEquals(10, dateTime.getHour());
        assertEquals(15, dateTime.getMinute());
        assertEquals(30, dateTime.getSecond());
    }

    @Test
    public void isTime() throws Exception
    {
        assertFalse(parameters.isTime("empty"));
        assertFalse(parameters.isTime("text"));
        assertFalse(parameters.isTime("time-out-bounds"));

        assertTrue(parameters.isTime("time-in-bounds"));
    }

    @Test
    public void getTime() throws Exception
    {
        LocalTime time = parameters.getTime("time-in-bounds"); // 23:59

        assertEquals(23, time.getHour());
        assertEquals(59, time.getMinute());
        assertEquals(0, time.getSecond());
    }

    @Test
    public void isEnum() throws Exception
    {
        assertFalse(parameters.isEnum("empty", Letters.class));
        assertFalse(parameters.isEnum("text", Letters.class));

        assertTrue(parameters.isEnum("A", Letters.class));
        assertTrue(parameters.isEnum("B", Letters.class));
        assertTrue(parameters.isEnum("C", Letters.class));
    }

    @Test
    public void getEnum() throws Exception
    {
        EnumSet<Letters> enumSet = EnumSet.allOf(Letters.class);

        assertEquals(Letters.A, parameters.getEnum("A", Letters.class));
        assertEquals(Letters.B, parameters.getEnum("B", Letters.class));
        assertEquals(Letters.C, parameters.getEnum("C", Letters.class));
    }

    private enum Letters
    {
        A,
        B,
        C,
    }
}