
package tvestergaard.fog.presentation.servlets;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.FileWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebListener()
public class StartUp implements ServletContextListener
{

    /**
     * The relative direction where log files are placed.
     */
    private final String logDirectory = "logs";

    /**
     * Receives notification that the web application initialization
     * process is starting.
     *
     * @param sce the ServletContextEvent containing the ServletContext that is being initialized
     */
    @Override public void contextInitialized(ServletContextEvent sce)
    {
        String realPath = sce.getServletContext().getRealPath("/");
        String time     = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm"));

        String errorFile = String.format("%s%s\\%s_%s.txt", realPath, logDirectory, time, "errors");
        String infoFile  = String.format("%s%s\\%s_%s.txt", realPath, logDirectory, time, "info");

        Configurator.defaultConfig()
                    .writer(new FileWriter(errorFile), Level.ERROR)
                    .addWriter(new FileWriter(infoFile), Level.INFO)
                    .activate();
    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     *
     * @param sce the ServletContextEvent containing the ServletContext that is being destroyed
     */
    @Override public void contextDestroyed(ServletContextEvent sce)
    {

    }
}
