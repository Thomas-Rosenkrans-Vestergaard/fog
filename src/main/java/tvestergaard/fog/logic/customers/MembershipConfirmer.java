package tvestergaard.fog.logic.customers;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.RegistrationToken;
import tvestergaard.fog.data.customers.RegistrationTokenDAO;

import java.time.LocalDateTime;

/**
 * Confirms the membership of some customer using the token sent to the customer by email.
 */
public class MembershipConfirmer
{

    /**
     * The DAO used to update the customer in data storage.
     */
    private final RegistrationTokenDAO dao;

    /**
     * The number of hours the token can be used from when it was first issued.
     */
    private final int EXPIRATION_TIME_HOURS = 24;

    /**
     * Creates a new {@link MembershipConfirmer}.
     *
     * @param dao The DAO used to update the customer in data storage.
     */
    public MembershipConfirmer(RegistrationTokenDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Confirms the membership matching the provided token details.
     *
     * @param id    The id of the token.
     * @param token The secret token.
     * @throws DataAccessException
     * @throws IncorrectTokenException
     * @throws ExpiredTokenException
     */
    public void confirm(int id, String token) throws DataAccessException, IncorrectTokenException, ExpiredTokenException
    {
        RegistrationToken tokenDB = dao.get(id);
        if (tokenDB == null)
            throw new IncorrectTokenException();

        LocalDateTime expiration = LocalDateTime.from(tokenDB.getCreatedAt().plusHours(EXPIRATION_TIME_HOURS));
        if (LocalDateTime.now().isAfter(expiration)) {
            throw new ExpiredTokenException();
        }

        if (!BCrypt.checkpw(token, tokenDB.getHash())) {
            throw new IncorrectTokenException();
        }

        dao.confirm(id);
    }
}
