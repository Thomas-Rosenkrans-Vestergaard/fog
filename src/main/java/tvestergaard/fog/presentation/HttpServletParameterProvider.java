package tvestergaard.fog.presentation;

import javax.servlet.http.HttpServletRequest;

public class HttpServletParameterProvider implements ParameterProvider
{

    private HttpServletRequest request;

    public HttpServletParameterProvider(HttpServletRequest request)
    {
        this.request = request;
    }

    /**
     * Returns the value of the provided parameter name.
     *
     * @param parameterName The name of the parameter to return the value of.
     * @return The value of the parameter. Returns {@code null} if no such parameter exists.
     */
    @Override
    public String getParameter(String parameterName)
    {
        return request.getParameter(parameterName);
    }

    /**
     * Returns an array of String objects containing all of the values the given request parameter has, or null if the
     * parameter does not exist. If the parameter has a single value, the array has a length of 1.
     *
     * @param parameterName The name of the parameter.
     * @return an array of String objects containing the parameter's values
     */
    @Override public String[] getParameterValues(String parameterName)
    {
        return request.getParameterValues(parameterName);
    }
}
