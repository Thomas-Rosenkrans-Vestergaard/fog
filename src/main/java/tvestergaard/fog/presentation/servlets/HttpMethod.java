package tvestergaard.fog.presentation.servlets;

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
