package tvestergaard.fog.presentation.servlets.commands;

public enum HttpMethod
{
    GET("GET"),
    POST("POST");

    private final String method;

    HttpMethod(String method)
    {
        this.method = method;
    }
}
