package tvestergaard.fog.data.customers;

import java.time.LocalDateTime;

public interface Customer extends CustomerUpdater
{

    /**
     * Returns the date time when the password of the customer was last updated.
     *
     * @return The date time when the password of the customer was last updated.
     */
    LocalDateTime getPasswordUpdatedAt();
}
