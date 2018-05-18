package tvestergaard.fog.presentation.servlets;

import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.writers.FileWriter;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class StartUp implements ServletContextListener
{

    /**
     * Receives notification that the web application initialization
     * process is starting.
     * <p>
     * <p>All ServletContextListeners are notified of context
     * initialization before any filters or servlets in the web
     * application are initialized.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being initialized
     */
    @Override public void contextInitialized(ServletContextEvent sce)
    {
        String realPath = sce.getServletContext().getRealPath("/");

        Configurator.defaultConfig()
                .writer(new FileWriter(realPath + "log\\log.txt"))
                .level(Level.WARNING)
                .activate();
    }

    /**
     * Receives notification that the ServletContext is about to be
     * shut down.
     * <p>
     * <p>All servlets and filters will have been destroyed before any
     * ServletContextListeners are notified of context
     * destruction.
     *
     * @param sce the ServletContextEvent containing the ServletContext
     *            that is being destroyed
     */
    @Override public void contextDestroyed(ServletContextEvent sce)
    {

    }
}
