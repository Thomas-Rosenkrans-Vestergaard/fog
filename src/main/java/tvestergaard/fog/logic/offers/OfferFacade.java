package tvestergaard.fog.logic.offers;

import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.constraints.Constraints;
import tvestergaard.fog.data.employees.Employee;
import tvestergaard.fog.data.employees.EmployeeColumn;
import tvestergaard.fog.data.employees.EmployeeDAO;
import tvestergaard.fog.data.employees.Role;
import tvestergaard.fog.data.offers.Offer;
import tvestergaard.fog.data.offers.OfferBlueprint;
import tvestergaard.fog.data.offers.OfferColumn;
import tvestergaard.fog.data.offers.OfferDAO;
import tvestergaard.fog.data.orders.Order;
import tvestergaard.fog.data.orders.OrderDAO;
import tvestergaard.fog.data.purchases.Purchase;
import tvestergaard.fog.data.tokens.TokenUse;
import tvestergaard.fog.logic.ApplicationException;
import tvestergaard.fog.logic.customers.InactiveCustomerException;
import tvestergaard.fog.logic.email.ApplicationMailer;
import tvestergaard.fog.logic.employees.InactiveEmployeeException;
import tvestergaard.fog.logic.employees.InsufficientPermissionsException;
import tvestergaard.fog.logic.employees.UnknownEmployeeException;
import tvestergaard.fog.logic.orders.UnknownOrderException;
import tvestergaard.fog.logic.tokens.*;
import tvestergaard.fog.presentation.Facades;

import java.util.List;
import java.util.Set;

import static tvestergaard.fog.data.constraints.Constraint.eq;
import static tvestergaard.fog.data.constraints.Constraint.where;
import static tvestergaard.fog.data.orders.OrderColumn.ID;

public class OfferFacade
{

    /**
     * The offer dao used to access the data storage in the application.
     */
    private final OfferDAO offerDAO;

    /**
     * The order dao used to validate the customers to issue offers to.
     */
    private final OrderDAO orderDAO;

    /**
     * The employee dao used to validate the employees issuing offers.
     */
    private final EmployeeDAO employeeDAO;

    /**
     * The object responsible for sending offer notification emails to customers.
     */
    private final ApplicationMailer mailer;

    /**
     * The object that validates offers when creating new offers.
     */
    private final OfferValidator validator = new OfferValidator();

    /**
     * The object responsible for creating one-time tokens for the offer email sent to customers.
     */
    private final TokenIssuer tokenIssuer;

    /**
     * The object responsible for authenticating one-time tokens for the offer email sent to customers.
     */
    private final TokenAuthenticator tokenAuthenticator;

    /**
     * Creates a new {@link OfferFacade}.
     *
     * @param offerDAO           The offer dao used to access the data storage in the application.
     * @param orderDAO           The order dao used to validate the customers to issue offers to.
     * @param employeeDAO        The employee dao used to validate the employees issuing offers.
     * @param mailer             The object responsible for sending offer notification emails to customers.
     * @param tokenIssuer        The object responsible for creating one-time tokens for the offer email sent to customers.
     * @param tokenAuthenticator The object responsible for authenticating one-time tokens for the offer email sent to customers.
     */
    public OfferFacade(
            OfferDAO offerDAO,
            OrderDAO orderDAO,
            EmployeeDAO employeeDAO,
            ApplicationMailer mailer,
            TokenIssuer tokenIssuer,
            TokenAuthenticator tokenAuthenticator)
    {
        this.offerDAO = offerDAO;
        this.orderDAO = orderDAO;
        this.employeeDAO = employeeDAO;
        this.mailer = mailer;
        this.tokenIssuer = tokenIssuer;
        this.tokenAuthenticator = tokenAuthenticator;
    }

    /**
     * Returns the offers in the data storage. The results can be constrained using the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The resulting offers.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Offer> get(Constraints<OfferColumn> constraints)
    {
        try {
            return offerDAO.get(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the offers in the data storage.
     *
     * @return The resulting offers.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List<Offer> get()
    {
        try {
            return offerDAO.get();
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the offers issued to the order with the provided id.
     *
     * @param order The id of the order to return the related offers of.
     * @return The offers related to the order with the provided id.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public List get(int order)
    {
        try {
            return offerDAO.get(order);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the first offer matching the provided constraints.
     *
     * @param constraints The constraints that modify the resulting list.
     * @return The first offer matching the provided constraints. Returns {@code null} when no constraints matches
     * the provided constraints.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public Offer first(Constraints<OfferColumn> constraints)
    {
        try {
            return offerDAO.first(constraints);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Inserts a new offer into the data storage.
     *
     * @param orderId    The id of the order the offer is issued for.
     * @param employeeId The id of the employee placing the offer.
     * @param price      The total cost of the offer.
     * @return The offer instance representing the newly created offer.
     * @throws ApplicationException             When a data storage exception occurs while performing the operation.
     * @throws OfferValidatorException          When the provided information is somehow invalid.
     * @throws UnknownOrderException            When the provided order id is unknown to the application.
     * @throws OrderNotActiveException          When the order is not active, and can therefor not be the subject of a new offer.
     * @throws InactiveCustomerException        When the provided customer is inactive, and can therefor not receive new
     *                                          offers.
     * @throws UnknownEmployeeException         When the provided employee id is unknown to the application.
     * @throws InactiveEmployeeException        When the provided employee is inactive, and can therefor not create new
     *                                          offers.
     * @throws InsufficientPermissionsException When the provided employee does not have the permissions required to
     *                                          create new offers.
     */
    public Offer create(int orderId, int employeeId, int price) throws
                                                                OfferValidatorException,
                                                                UnknownOrderException,
                                                                OrderNotActiveException,
                                                                InactiveCustomerException,
                                                                UnknownEmployeeException,
                                                                InactiveEmployeeException,
                                                                InsufficientPermissionsException
    {
        Set<OfferError> errors = validator.validate(price);
        if (!errors.isEmpty())
            throw new OfferValidatorException(errors);

        try {

            Order order = orderDAO.first(where(eq(ID, orderId)));
            if (order == null)
                throw new UnknownOrderException();
            if (!order.isActive())
                throw new OrderNotActiveException();
            if (!order.getCustomer().isActive())
                throw new InactiveCustomerException();

            Employee employee = employeeDAO.first(where(eq(EmployeeColumn.ID, employeeId)));
            if (employee == null)
                throw new UnknownEmployeeException();
            if (!employee.isActive())
                throw new InactiveEmployeeException();
            if (!employee.is(Role.SALESMAN))
                throw new InsufficientPermissionsException(Role.SALESMAN);

            Offer                  offer = offerDAO.create(OfferBlueprint.from(orderId, employeeId, price));
            TokenPair              token = tokenIssuer.issue(order.getCustomer(), TokenUse.OFFER_EMAIL);
            OfferNotificationEmail email = new OfferNotificationEmail(offer, token);
            mailer.send(email);
            return offer;
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Rejects the offer with the provided id.
     * <p>
     * To accept an offer, use the {@link tvestergaard.fog.logic.purchases.PurchaseFacade#create(int)} method,
     * that will mark the provided offer accepted.
     *
     * @param offerId The id of the offer to mark reject.
     * @throws ApplicationException  When a data storage exception occurs while performing the operation.
     * @throws UnknownOfferException When the provided offer id does not exist in the application.
     * @throws OfferNotOpenException When the provided offer is not open, and can therefor not be rejected.
     */
    public void reject(int offerId) throws UnknownOfferException, OfferNotOpenException
    {
        try {

            Offer offer = offerDAO.first(where(eq(OfferColumn.ID, offerId)));
            if (offer == null)
                throw new UnknownOfferException();
            if (!offer.isOpen())
                throw new OfferNotOpenException();

            offerDAO.reject(offerId);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Rejects the offer using the provided token.
     *
     * @param offer       The if of the offer being rejected.
     * @param tokenId     The token id to use when rejecting the offer.
     * @param tokenSecret The token secret to use when rejecting the offer.
     * @throws ApplicationException    When some data storage exception occurs.
     * @throws ExpiredTokenException   When the provided token has expired.
     * @throws IncorrectTokenException When the provided token is invalid.
     * @throws OfferNotOpenException   When the offer is not in an open state, and can therefor not be rejected.
     * @throws UnknownOfferException   WHen the provided offer id is unknown to the application.
     */
    public void reject(int offer, int tokenId, String tokenSecret) throws ExpiredTokenException,
                                                                          IncorrectTokenException,
                                                                          OfferNotOpenException,
                                                                          UnknownOfferException
    {
        try {
            if (!tokenAuthenticator.authenticate(new TokenPair(tokenId, tokenSecret), TokenUse.OFFER_EMAIL))
                throw new IncorrectTokenException();

            reject(offer);

        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Accepts the offer using the provided token.
     *
     * @param offer       The if of the offer being accepted.
     * @param tokenId     The token id to use when accepting the offer.
     * @param tokenSecret The token secret to use when accepting the offer.
     * @return The purchase record.
     * @throws ApplicationException             When some data storage exception occurs.
     * @throws ExpiredTokenException            When the provided token has expired.
     * @throws IncorrectTokenException          When the provided token is invalid.
     * @throws OfferNotOpenException            When the offer is not in an open state, and can therefor not be accepted.
     * @throws InsufficientPermissionsException When the employee who created the offer is no longer a salesman.l
     * @throws UnknownOfferException            WHen the provided offer id is unknown to the application.
     * @throws InactiveCustomerException        When the customer accepting the offer, is not active.
     */
    public Purchase accept(int offer, int tokenId, String tokenSecret) throws ExpiredTokenException,
                                                                              IncorrectTokenException,
                                                                              InsufficientPermissionsException,
                                                                              OfferNotOpenException,
                                                                              UnknownOfferException,
                                                                              InactiveCustomerException
    {
        try {
            if (!tokenAuthenticator.authenticate(new TokenPair(tokenId, tokenSecret), TokenUse.OFFER_EMAIL))
                throw new IncorrectTokenException();

            return Facades.purchaseFacade.create(offer);

        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Returns the number of open offers for the provided customer.
     *
     * @param customer The customer to return the number of open offers for.
     * @return The number of open offers for the provided customer.
     * @throws ApplicationException When a data storage exception occurs while performing the operation.
     */
    public int getNumberOfOpenOffers(int customer)
    {
        try {
            return offerDAO.getNumberOfOpenOffers(customer);
        } catch (DataAccessException e) {
            throw new ApplicationException(e);
        }
    }
}
