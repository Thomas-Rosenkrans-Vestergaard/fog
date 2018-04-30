package tvestergaard.fog.presentation.servlets;

import tvestergaard.fog.data.customers.Customer;
import tvestergaard.fog.logic.customers.*;
import tvestergaard.fog.presentation.Notifications;
import tvestergaard.fog.presentation.Parameters;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static tvestergaard.fog.logic.customers.CustomerError.*;
import static tvestergaard.fog.presentation.PresentationFunctions.notifications;

@WebServlet(urlPatterns = "/account")
public class AccountServlet extends HttpServlet
{

    private final CustomerFacade             facade        = new CustomerFacade();
    private final Map<CustomerError, String> errorMessages = new HashMap<>();

    public AccountServlet()
    {
        errorMessages.put(NAME_EMPTY, "Det sendte navn må ikke være tomt.");
        errorMessages.put(NAME_LONGER_THAN_255, "Det sendte navn er for langt.");
        errorMessages.put(ADDRESS_EMPTY, "Den sendte adresse må ikke være tom.");
        errorMessages.put(ADDRESS_LONGER_THAN_255, "Den sendte adresse er for lang.");
        errorMessages.put(EMAIL_INVALID, "Den sendte email er ikke formateret korrekt.");
        errorMessages.put(EMAIL_TAKEN, "Den sendte email er allerede i bruge  på siden.");
        errorMessages.put(EMAIL_LONGER_THAN_255, "Den sendte email er for lang.");
        errorMessages.put(PHONE_EMPTY, "Det sendte telefonnummer må ikke være tomt.");
        errorMessages.put(PHONE_LONGER_THAN_30, "Det sendte telefonnummer er for langt.");
        errorMessages.put(PASSWORD_SHORTER_THAN_4, "Det sendte password er for kort.");
    }

    /**
     * Displays the /account page where customers can create and log in to their account.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Parameters    parameters    = new Parameters(req);
        Notifications notifications = notifications(req);

        String action = parameters.value("action");


        if ("confirm-membership".equals(action)) {
            confirmMembership(req, resp, parameters, notifications);
            return;
        }

        if ("reject-membership".equals(action)) {
            rejectMembership(req, resp, parameters, notifications);
            return;
        }

        req.setAttribute("title", "Kundekonto");
        req.getRequestDispatcher("/WEB-INF/account.jsp").forward(req, resp);
    }

    /**
     * Processes the information sent by the customer on the /account page.
     *
     * @param req  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
     * @param resp an {@link HttpServletResponse} object that contains the response the servlet sends to the client
     * @throws IOException      if an input or output error is detected when the servlet handles the GET request
     * @throws ServletException if the request for the GET could not be handled
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Parameters    parameters    = new Parameters(req);
        Notifications notifications = notifications(req);

        if (!parameters.isPresent("action")) {
            notifications.error("Incomplete form post.");
            resp.sendRedirect("account");
            return;
        }

        String action = parameters.value("action");

        if (action.equals("registration")) {
            processRegistration(req, resp, parameters, notifications);
            return;
        }

        if (action.equals("login")) {
            processLogin(req, resp, parameters, notifications);
            return;
        }
    }

    private void confirmMembership(HttpServletRequest req,
                                   HttpServletResponse resp,
                                   Parameters parameters,
                                   Notifications notifications) throws ServletException, IOException
    {
        if (!parameters.isInt("id") || !parameters.isPresent("token")) {
            notifications.error("Bad challenge token.");
            resp.sendRedirect("profile");
            return;
        }

        try {
            facade.confirm(parameters.getInt("id"), parameters.value("token"));
            notifications.success("Din kundekonto blev bekræftet.");
            return;
        } catch (IncorrectTokenException e) {
            notifications.error("The token was incorrect.");
            resp.sendRedirect("profile");
        } catch (ExpiredTokenException e) {
            notifications.error("The token was expired.");
            resp.sendRedirect("profile");
        }
    }


    private void rejectMembership(HttpServletRequest req,
                                  HttpServletResponse resp,
                                  Parameters parameters,
                                  Notifications notifications)
            throws ServletException, IOException
    {
        if (!parameters.isInt("id") || !parameters.isPresent("token")) {
            notifications.error("Bad challenge token.");
            resp.sendRedirect("");
            return;
        }

        try {
            facade.reject(parameters.getInt("id"), parameters.value("token"));
            notifications.success("Din kundekonto blev deaktiveret.");
            return;
        } catch (IncorrectTokenException e) {
            notifications.error("The token was incorrect.");
            resp.sendRedirect("profile");
        } catch (ExpiredTokenException e) {
            notifications.error("The token was expired.");
            resp.sendRedirect("profile");
        }
    }

    private void processLogin(HttpServletRequest req,
                              HttpServletResponse resp,
                              Parameters parameters,
                              Notifications notifications) throws ServletException, IOException
    {
        if (!parameters.isPresent("email") || !parameters.isPresent("password")) {
            notifications.error("Incomplete form post.");
            resp.sendRedirect("account");
            return;
        }

        try {
            Customer customer = facade.authenticate(parameters.value("email"), parameters.value("password"));
            if (customer == null) {
                notifications.error("Ukorrekte akkreditiver.");
                resp.sendRedirect("account");
                return;
            }

            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);
            notifications.success("Du er nu logget ind.");
            resp.sendRedirect("profile");

        } catch (NoPasswordException e) {
            notifications.error("Denne konto har intet password.");
            resp.sendRedirect("forgot-password");
        } catch (InactiveCustomerException e){
            notifications.error("Denne konto er inaktiv.");
            resp.sendRedirect("account");
        }
    }


    private void processRegistration(HttpServletRequest req,
                                     HttpServletResponse resp,
                                     Parameters parameters,
                                     Notifications notifications) throws ServletException, IOException
    {
        if (!parameters.isPresent("name") ||
                !parameters.isPresent("address") ||
                !parameters.isPresent("email") ||
                !parameters.isPresent("phone") ||
                !parameters.isPresent("password")) {
            notifications.error("Incomplete form post.");
            resp.sendRedirect("account");
            return;
        }

        try {

            Customer customer = facade.register(
                    parameters.value("name"),
                    parameters.value("address"),
                    parameters.value("email"),
                    parameters.value("phone"),
                    parameters.value("password"),
                    true);

            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);
            notifications.success("Kundekontoen blev oprettet.");
            resp.sendRedirect("profile");

        } catch (CustomerValidatorException e) {
            for (CustomerError error : e.getErrors())
                notifications.error(errorMessages.get(error));
            resp.sendRedirect("account");
            return;
        }
    }
}
