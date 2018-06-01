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
     * @param base The base url of the website. The method ensures that the url ends with a slash.
     */
    public WebsiteContext(String base)
    {
        this.base = base.replaceAll("[\\/]*$", "") + '/';
    }

    /**
     * Returns the base url of the website. Note that the returned url always has a trailing slash.
     *
     * @return The base url of the website.
     */
    public String getBase()
    {
        return this.base;
    }
}
