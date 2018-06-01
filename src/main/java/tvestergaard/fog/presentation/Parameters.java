package tvestergaard.fog.presentation;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Class for validating types and converting the values of parameters in the provided {@code HttpServletRequest}.
 */
public class Parameters
{

    /**
     * The {@code HttpServletRequest} from where to access the parameters to validate.
     */
    private final ParameterProvider provider;

    /**
     * Creates a new {@link Parameters}.
     *
     * @param provider The {@link ParameterProvider} that provides the parameters the {@link Parameters} class performs
     *                 operations upon.
     */
    public Parameters(ParameterProvider provider)
    {
        this.provider = provider;
    }

    /**
     * Creates a new {@link Parameters}.
     *
     * @param request The {@code HttpServletRequest} from where to access the parameters to validate.
     */
    public Parameters(HttpServletRequest request)
    {
        this.provider = new HttpServletParameterProvider(request);
    }

    /**
     * Checks if the provided parameter was sent.
     *
     * @param parameter The name of the parameter to check for.
     * @return {@code true} if the provided parameter was sent.
     */
    public boolean isPresent(String parameter)
    {
        return getParameterValue(parameter) != null;
    }

    /**
     * Returns the string value of the provided parameter name.
     *
     * @param parameter The name of the parameter to return the string value of.
     * @return The string value of the provided parameter name.
     */
    public String value(String parameter)
    {
        return provider.getParameter(parameter);
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to an integer value using the {@link
     * Parameters#getInt(String)} method.
     *
     * @param parameter The name of the parameter to test the format of.
     * @return {@code true} if the value of the provided parameter can safely be extracted to an integer value using the
     * {@link Parameters#getInt(String)} method.
     */
    public boolean isInt(String parameter)
    {
        try {
            Integer.parseInt(getParameterValue(parameter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the integer value of the value of the provided parameter
     * <p>
     * This method will not throw an exception when the {@link Parameters#isInt(String)} method returns true for the
     * same parameter name.
     *
     * @param parameter The name of the parameter to extract the int value from.
     * @return The int value of the value of the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the int value.
     */
    public int getInt(String parameter)
    {
        try {
            return Integer.parseInt(getParameterValue(parameter));
        } catch (Exception e) {
            throw new ParametersConversionException(e);
        }
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to a long value using the {@link
     * Parameters#getLong(String)} method.
     *
     * @param parameter The name of the parameter to test the format of.
     * @return {@code true} if the value of the provided parameter can safely be extracted to a long value using the
     * {@link Parameters#getLong(String)} method.
     */
    public boolean isLong(String parameter)
    {
        try {
            Long.parseLong(getParameterValue(parameter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the long value of the value of the provided parameter.
     * <p>
     * This method will not throw an exception when the {@link Parameters#isLong(String)} method returns true for the
     * same parameter name.
     *
     * @param parameter The name of the parameter to extract the long value from.
     * @return The long value of the value of the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the long value.
     */
    public long getLong(String parameter)
    {
        try {
            return Long.parseLong(getParameterValue(parameter));
        } catch (Exception e) {
            throw new ParametersConversionException(e);
        }
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to a float value using the {@link
     * Parameters#getFloat(String)} method.
     *
     * @param parameter The name of the parameter to test the format of.
     * @return {@code true} if the value of the provided parameter can safely be extracted to a float value using the
     * {@link Parameters#getFloat(String)} method.
     */
    public boolean isFloat(String parameter)
    {
        try {
            Float.parseFloat(getParameterValue(parameter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the float value of the value of the provided parameter.
     * <p>
     * This method will not throw an exception when the {@link Parameters#isFloat(String)} method returns true for the
     * same parameter name.
     *
     * @param parameter The name of the parameter to extract the float value from.
     * @return The float value of the value of the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the float value.
     */
    public float getFloat(String parameter)
    {
        try {
            return Float.parseFloat(getParameterValue(parameter));
        } catch (Exception e) {
            throw new ParametersConversionException(e);
        }
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to a double value using the {@link
     * Parameters#getDouble(String)} method.
     *
     * @param parameter The name of the parameter to test the format of.
     * @return {@code true} if the value of the provided parameter can safely be extracted to a double value using the
     * {@link Parameters#getDouble(String)} method.
     */
    public boolean isDouble(String parameter)
    {
        try {
            Double.parseDouble(getParameterValue(parameter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the double value of the value of the provided parameter.
     * <p>
     * This method will not throw an exception when the {@link Parameters#isDouble(String)} method returns true for the
     * same parameter name.
     *
     * @param parameter The name of the parameter to extract the double value from.
     * @return The double value of the value of the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the double value.
     */
    public double getDouble(String parameter)
    {
        try {
            return Double.parseDouble(getParameterValue(parameter));
        } catch (Exception e) {
            throw new ParametersConversionException(e);
        }
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to a boolean value using the {@link
     * Parameters#getBoolean(String)} method.
     * <p>
     * The values that can be converted to a boolean value are:
     * <pre>
     * - "true" : true
     * - "on"   : true
     * - "false": false
     * - "off"  : false
     * </pre>
     *
     * @param parameter The name of the parameter to test the format of.
     * @return {@code true} if the value of the provided parameter can safely be extracted to a boolean value using the
     * {@link Parameters#getBoolean(String)} method.
     */
    public boolean isBoolean(String parameter)
    {
        String value = getParameterValue(parameter);

        return "true".equals(value) || "on".equals(value) || "false".equals(value) || "off".equals(value);
    }

    /**
     * Extracts the boolean value of the value of the provided parameter.
     * <p>
     * This method will not throw an exception when the {@link Parameters#isBoolean(String)} method returns true for the
     * same parameter name.
     *
     * @param parameter The name of the parameter to extract the boolean value from.
     * @return The boolean value of the value of the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the boolean value.
     */
    public boolean getBoolean(String parameter)
    {
        String value = getParameterValue(parameter);

        if ("true".equals(value) || "on".equals(value))
            return true;

        if ("false".equals(value) || "off".equals(value))
            return false;

        throw new ParametersConversionException(null);
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to a {@link LocalDate} using the {@link
     * Parameters#getDate(String)} method.
     *
     * @param parameter The name of the parameter to test the format of.
     * @return {@code true} if the value of the provided parameter can safely be extracted to a {@link LocalDate} using
     * the {@link Parameters#getDate(String)} method.
     */
    public boolean isDate(String parameter)
    {
        try {
            LocalDate.parse(getParameterValue(parameter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the date value of the value of the provided parameter to a {@link LocalDate}.
     * <p>
     * This method will not throw an exception when the {@link Parameters#isDate(String)} method returns true for the
     * same parameter name.
     *
     * @param parameter The name of the parameter to extract the date value from.
     * @return The date value of the value of the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the date value.
     */
    public LocalDate getDate(String parameter)
    {
        try {
            return LocalDate.parse(getParameterValue(parameter));
        } catch (Exception e) {
            throw new ParametersConversionException(e);
        }
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to a {@link LocalDateTime} using the
     * {@link Parameters#getDatetime(String)} method.
     *
     * @param parameter The name of the parameter to test the format of.
     * @return {@code true} if the value of the provided parameter can safely be extracted using the {@link
     * Parameters#getDatetime(String)} method.
     */
    public boolean isDatetime(String parameter)
    {
        try {
            LocalDateTime.parse(getParameterValue(parameter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the datetime value of the value of the provided parameter to a {@link LocalDateTime}.
     * <p>
     * This method will not throw an exception when the {@link Parameters#isDatetime(String)} method returns true for
     * the same parameter name.
     *
     * @param parameter The name of the parameter to extract the datetime value from.
     * @return The datetime value of the value of the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the datetime value.
     */
    public LocalDateTime getDatetime(String parameter)
    {
        try {
            return LocalDateTime.parse(getParameterValue(parameter));
        } catch (Exception e) {
            throw new ParametersConversionException(e);
        }
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to a {@link LocalTime} using the {@link
     * Parameters#getTime(String)} method.
     *
     * @param parameter The name of the parameter to test the format of.
     * @return {@code true} if the value of the provided parameter can safely be extracted to a {@link LocalTime} using
     * the {@link Parameters#getTime(String)} method.
     */
    public boolean isTime(String parameter)
    {
        try {
            LocalTime.parse(getParameterValue(parameter));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extracts the time value of the value of the provided parameter to a {@link LocalTime}.
     * <p>
     * This method will not throw an exception when the {@link Parameters#isTime(String)} method returns true for the
     * same parameter name.
     *
     * @param parameter The name of the parameter to extract the time value from.
     * @return The time value of the value of the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the time value.
     */
    public LocalTime getTime(String parameter)
    {
        try {
            return LocalTime.parse(getParameterValue(parameter));
        } catch (Exception e) {
            throw new ParametersConversionException(e);
        }
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to the type {@code T} using the {@link
     * Parameters#getEnum(String, Class)} method. The enum element is considered a match when the name of the enum
     * element equals the value of the parameter.
     *
     * @param parameter   The name of the parameter to test the format of.
     * @param enumeration The {@link EnumSet} containing the legal values.
     * @param <T>         The type of the enum type.
     * @return {@code true} if the value of the provided parameter can safely be extracted to the type {@code T} using
     * the {@link Parameters#getEnum(String, Class)}
     */
    public <T extends Enum<T>> boolean isEnum(String parameter, Class<T> enumeration)
    {
        if (!isPresent(parameter))
            return false;

        String value = getParameterValue(parameter);
        for (T element : EnumSet.allOf(enumeration))
            if (element.name().equals(value))
                return true;

        return false;
    }

    /**
     * Extracts the {@code T} value of the provided parameter. The enum element is considered a match when the name of
     * the enum element equals the value of the parameter.
     * <p>
     * This method will not throw an exception when the {@link Parameters#isEnum(String, Class)} method returns true
     * for the same parameter name.
     *
     * @param parameter   The name of the parameter to extract the double value from.
     * @param enumeration The enum.
     * @return The double value of the value of the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the {@code T} value.
     */
    public <T extends Enum<T>> T getEnum(String parameter, Class<T> enumeration)
    {
        String value = getParameterValue(parameter);
        for (T element : EnumSet.allOf(enumeration))
            if (element.name().equals(value))
                return element;

        throw new ParametersConversionException(null);
    }

    /**
     * Checks that the value of the provided parameter can safely be extracted to an array of enums using the {@link
     * Parameters#getEnums(String, Class)} method.
     *
     * @param parameter   The name of the parameter to test the format of.
     * @param enumeration The class of the enumeration to check.
     * @param <T>         The type of the enumeration to check.
     * @return {@code true} if the value of the provided parameter can safely be extracted to an array of enums using
     * the {@link Parameters#getEnums(String, Class)} method.
     */
    public <T extends Enum<T>> boolean isEnums(String parameter, Class<T> enumeration)
    {
        String[] parameterValues = provider.getParameterValues(parameter);

        if (parameter == null)
            return false;

        EnumSet<T> enumSet = EnumSet.allOf(enumeration);
        for (String parameterValue : parameterValues)
            if (!isEnum(parameterValue, enumSet))
                return false;

        return true;
    }

    public <T extends Enum<T>> boolean isEnum(String value, EnumSet<T> enumSet)
    {
        for (T enumElement : enumSet) {
            if (enumElement.name().equals(value)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Extracts the time value of the value of the provided parameter to an array of enums.
     * <p>
     * This method will not throw an exception when the {@link Parameters#isEnums(String, Class)} method returns true
     * for the same parameter name.
     *
     * @param parameter   The name of the parameter to extract the enum values from.
     * @param enumeration The class of the enumeration to return.
     * @param <T>         The type of the enumeration to return.
     * @return The resulting array of enums from the provided parameter.
     * @throws ParametersConversionException When an error occurs while extracting the time value.
     */
    public <T extends Enum<T>> Set<T> getEnums(String parameter, Class<T> enumeration) throws ParametersConversionException
    {
        try {
            String[] parameterValues = provider.getParameterValues(parameter);
            Set<T>   result          = new HashSet<>();
            for (int x = 0; x < parameterValues.length; x++) {
                result.add(Enum.valueOf(enumeration, parameterValues[x]));
            }
            return result;
        } catch (Exception e) {
            throw new ParametersConversionException(e);
        }
    }

    /**
     * Returns the string value of the provided parameter name.
     *
     * @param parameterName The name of the parameter to return the string value of.
     * @return The string value of the provided parameter name.
     */
    protected String getParameterValue(String parameterName)
    {
        return provider.getParameter(parameterName);
    }
}
