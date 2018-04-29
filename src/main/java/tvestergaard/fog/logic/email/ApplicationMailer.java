package tvestergaard.fog.logic.email;

public interface ApplicationMailer
{

    /**
     * Sends the provided application email.
     *
     * @param email The email to send.
     */
    void send(ApplicationEmail email);
}
