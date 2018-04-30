package tvestergaard.fog.presentation;

public interface ParameterProvider
{

    /**
     * Returns the value of the provided parameter name.
     *
     * @param parameterName The name of the parameter to return the value of.
     * @return The value of the parameter. Returns {@code null} if no such parameter exists.
     */
    String getParameter(String parameterName);

    /**
     * Returns an array of String objects containing all of the values the given request parameter has, or null if the
     * parameter does not exist. If the parameter has a single value, the array has a length of 1.
     *
     * @param parameterName The name of the parameter.
     * @return an array of String objects containing the parameter's values
     */
    String[] getParameterValues(String parameterName);
}
