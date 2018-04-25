package tvestergaard.fog.presentation.tables;

public class HTML
{

    public static String link(String href, String contents)
    {
        return String.format("<a href='%s'>%s</a>", href, contents);
    }
}
