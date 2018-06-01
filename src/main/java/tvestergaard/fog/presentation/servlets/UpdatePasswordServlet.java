package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.data.customers.UnknownCustomerException;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.customers.CustomerAuthenticationException;
import tvestergaard.fog.logic.customers.CustomerFacade;
import tvestergaard.fog.presentation.Authentication;
import tvestergaard.fog.presentation.Facades;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tvestergaard.fog.presentation.PresentationFunctions.*;

@WebServlet(urlPatterns = "/update-password")
public class UpdatePasswordServlet extends HttpServlet
{

    private final CustomerFacade customerFacade = Facades.customerFacade;

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        if (authentication.redirect(resp))
            return;

        Customer customer = authentication.getCustomer();
        req.setAttribute("title", "Opdater password");
        req.setAttribute("nagivation", "update_password");
        req.setAttribute("customer", customer);
        req.setAttribute("context", ".");
        req.setAttribute("csrf", csrf(req));
        req.getRequestDispatcher("/WEB-INF/update_password.jsp").forward(req, resp);
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Authentication authentication = new Authentication(req);
        if (authentication.redirect(resp))
            return;

        Customer      customer      = authentication.getCustomer();
        Parameters    parameters    = new Parameters(req);
        Notifications notifications = notifications(req);

        if (!verify(req)) {
            resp.sendRedirect("update-password");
            return;
        }

        if (!parameters.isInt("id") ||
                !parameters.isPresent("old-password") ||
                !parameters.isPresent("new-password")) {
            notifications.error("Not enough information.");
            resp.sendRedirect("update-password");
            return;
        }

        if (parameters.getInt("id") != customer.getId()) {
            notifications.error("Not your user account.");
            resp.sendRedirect("update-password");
            return;
        }

        try {
            customerFacade.updatePassword(customer.getId(), parameters.value("old-password"),
                    parameters.value("new-password"));
            notifications.success("Din adgangskode blev opdateret.");
            resp.sendRedirect("profile");
            return;
        } catch (ApplicationException e) {
            notifications.error("ApplicationException");
        } catch (UnknownCustomerException e) {
            notifications.error("Ukendt kunde.");
        } catch (CustomerAuthenticationException e) {
            notifications.error("Den givne adgangskode er ukorrekt.");
        }

        resp.sendRedirect("update-password");
    }
}
