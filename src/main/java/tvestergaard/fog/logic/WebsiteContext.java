package tvestergaard.fog.logic;

public class WebsiteContext
{

    /**
     * The base url of the website.
     */
    private final String base;

    /**
     * Creates a new {@link WebsiteContext}.
     *
     * @param base The base url of the website.
     */
    public WebsiteContext(String base)
    {
        this.base = base.replace("\\/", "") + '/';
    }

    /**
     * Returns the base url of the website.
     *
     * @return The base url of the website.
     */
    public String getBase()
    {
        return this.base;
    }
}
