package tvestergaard.fog.logic.email;

import tvestergaard.fog.logic.WebsiteContext;

import static org.apache.commons.text.StringEscapeUtils.escapeHtml4;

public class CurrentEmailTemplate implements EmailTemplate
{

    /**
     * Information about the fog website.
     */
    private final WebsiteContext websiteContext;

    /**
     * Creates a new {@link CurrentEmailTemplate}.
     *
     * @param websiteContext Information about the fog website.
     */
    public CurrentEmailTemplate(WebsiteContext websiteContext)
    {
        this.websiteContext = websiteContext;
    }

    /**
     * Renders the template using the provided title and contents.
     *
     * @param title    The title to place in the template.
     * @param contents The html contents to insert into the template.
     * @return The resulting html.
     */
    @Override public String render(String title, String contents)
    {
        StringBuilder builder = new StringBuilder();

        builder.append("<div style='font-family: Arial, Helvetica, sans-serif;margin:0 auto;max-width: 720px'>");
        builder.append("<div style='background: #00498a'>");
        builder.append("<img src='");
        builder.append(websiteContext.getBase());
        builder.append("resources/logo_small.png' alt='Fog logo' style='display:block; margin: 0 auto;'>");
        builder.append("</div>");
        builder.append("<h1>");
        builder.append(escapeHtml4(title));
        builder.append("</h1>");
        builder.append("<div>");
        builder.append(contents);
        builder.append("</div>");
        builder.append("</div>");

        return builder.toString();
    }
}
