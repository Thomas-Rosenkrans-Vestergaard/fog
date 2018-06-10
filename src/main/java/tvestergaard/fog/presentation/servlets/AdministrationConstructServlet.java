package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.cladding.Cladding;
import tvestergaard.fog.data.cladding.CladdingColumn;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.flooring.Flooring;
import tvestergaard.fog.data.orders.Shed;
import tvestergaard.fog.data.orders.ShedRecord;
import tvestergaard.fog.data.roofing.Roofing;
import tvestergaard.fog.data.roofing.RoofingColumn;
import tvestergaard.fog.logic.claddings.CladdingFacade;
import tvestergaard.fog.logic.construction.ConstructionException;
import tvestergaard.fog.logic.construction.ConstructionFacade;
import tvestergaard.fog.logic.construction.ConstructionSpecification;
import tvestergaard.fog.logic.construction.GarageConstructionSummary;
import tvestergaard.fog.logic.floorings.FlooringFacade;
import tvestergaard.fog.logic.roofings.RoofingFacade;
import tvestergaard.fog.presentation.Facades;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.roofing.RoofingColumn.ID;
import static tvestergaard.fog.presentation.PresentationFunctions.csrf;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/administration/construct")
public class AdministrationConstructServlet extends AdministrationServlet
{

    private final ConstructionFacade constructionFacade = Facades.constructionFacade;
    private final FlooringFacade     flooringFacade     = Facades.flooringFacade;
    private final CladdingFacade     claddingFacade     = Facades.claddingFacade;
    private final RoofingFacade      roofingFacade      = Facades.roofingFacade;

    @Override protected boolean before(HttpServletRequest req, HttpServletResponse resp, Employee employee) throws ServletException, IOException
    {
        req.setAttribute("context", "..");
        req.setAttribute("navigation", "administration_construct");

        return true;
    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        if (!isAuthenticated(req, resp))
            return;

        req.setAttribute("context", "..");
        if (!before(req, resp, (Employee) req.getSession().getAttribute("employee")))
            return;

        Parameters parameters = new Parameters(req);

        if (parameters.isPresent("submit")) {
            showResult(parameters, req, resp);
            return;
        }

        req.setAttribute("title", "Konstruer carport");
        req.setAttribute("csrf", csrf(req));
        req.setAttribute("roofings", roofingFacade.get());
        req.setAttribute("claddings", claddingFacade.get());
        req.setAttribute("floorings", flooringFacade.get());
        req.getRequestDispatcher("/WEB-INF/administration/construct.jsp").forward(req, resp);
    }

    private void showResult(Parameters parameters, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Notifications notifications = notifications(req);

        if (!parameters.isInt("width") ||
                !parameters.isInt("length") ||
                !parameters.isInt("height") ||
                !parameters.isInt("roofing") ||
                !parameters.isInt("slope")) {

            notifications.error("Invalid design data.");
            resp.sendRedirect("construct");
            return;
        }

        if (parameters.isPresent("shed")) {
            if (!parameters.isInt("shed-depth") ||
                    !parameters.isInt("shed-roofing") ||
                    !parameters.isInt("shed-cladding")) {
                notifications.error("Invalid design data.");
                resp.sendRedirect("construct");
                return;
            }
        }

        Roofing roofing = roofingFacade.get(parameters.getInt("roofing"));

        ConstructionSpecification specification = new ConstructionSpecification(
                parameters.getInt("width"),
                parameters.getInt("length"),
                parameters.getInt("height"),
                roofing,
                parameters.getInt("slope"),
                createShed(parameters));

        try {
            GarageConstructionSummary summary = constructionFacade.construct(specification);
            req.setAttribute("summary", summary);
            req.setAttribute("title", "Resultat");
            req.getRequestDispatcher("/WEB-INF/administration/construct_summary.jsp").forward(req, resp);
            return;
        } catch (ConstructionException e) {
            notifications.error(e.getClass().getCanonicalName()); // TODO: fix
        }

        resp.sendRedirect("construct");
    }

    private Shed createShed(Parameters parameters)
    {
        if (!parameters.isPresent("shed"))
            return null;

        Cladding cladding = claddingFacade.get(parameters.getInt("shed-cladding"));
        Flooring flooring = flooringFacade.get(parameters.getInt("shed-roofing"));

        return new ShedRecord(-1, parameters.getInt("shed-depth"), cladding.getId(), cladding, flooring.getId(), flooring);
    }
}
