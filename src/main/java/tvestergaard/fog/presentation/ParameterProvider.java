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
}
