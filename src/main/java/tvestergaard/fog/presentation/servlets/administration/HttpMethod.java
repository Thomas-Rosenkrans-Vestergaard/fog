package tvestergaard.fog.presentation.servlets.administration;

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
