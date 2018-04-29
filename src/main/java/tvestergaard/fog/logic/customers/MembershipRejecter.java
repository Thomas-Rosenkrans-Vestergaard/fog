package tvestergaard.fog.logic.customers;

import org.mindrot.jbcrypt.BCrypt;
import tvestergaard.fog.data.DataAccessException;
import tvestergaard.fog.data.customers.RegistrationToken;
import tvestergaard.fog.data.customers.RegistrationTokenDAO;

import java.time.LocalDateTime;

public class MembershipRejecter
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
     * Creates a new {@link MembershipRejecter}.
     *
     * @param dao The DAO used to update the customer in data storage.
     */
    public MembershipRejecter(RegistrationTokenDAO dao)
    {
        this.dao = dao;
    }

    /**
     * Rejects the membership matching the provided token details.
     *
     * @param id    The id of the token.
     * @param token The secret token.
     * @throws DataAccessException
     * @throws IncorrectTokenException
     * @throws ExpiredTokenException
     */
    public void reject(int id, String token) throws DataAccessException, IncorrectTokenException, ExpiredTokenException
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

        dao.reject(id);
    }
}
