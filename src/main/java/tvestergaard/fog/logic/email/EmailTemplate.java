package tvestergaard.fog.logic.email;

public interface EmailTemplate
{

    /**
     * Renders the template using the provided title and contents.
     *
     * @param title    The title to place in the template.
     * @param contents The html contents to insert into the template.
     * @return The resulting html.
     */
    String render(String title, String contents);
}
