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
}
