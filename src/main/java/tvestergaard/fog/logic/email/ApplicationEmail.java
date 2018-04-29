package tvestergaard.fog.logic.email;

import org.simplejavamail.email.Email;

public interface ApplicationEmail
{

    /**
     * Builds the Email instance that can be sent using the SimpleJavaMail library.
     *
     * @return The email instance.
     */
    Email build();
}
