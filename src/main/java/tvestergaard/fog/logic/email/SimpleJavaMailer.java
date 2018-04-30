package tvestergaard.fog.logic.email;

import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

public class SimpleJavaMailer implements ApplicationMailer
{

    /**
     * The simple java mailer that actually sends the email.
     */
    private final Mailer mailer;

    private static final String HOST = "smtp.gmail.com";
    private static final int    PORT = 587;
    private static final String USER = "fog.carporte@gmail.com";
    private static final String PASS = "m48yXLaX";

    /**
     * Creates a new {@link SimpleJavaMailer}.
     */
    public SimpleJavaMailer()
    {
        this.mailer = new Mailer(HOST, PORT, USER, PASS, TransportStrategy.SMTP_TLS);
    }

    /**
     * Sends the provided application email.
     *
     * @param email The email to send.
     */
    @Override public void send(ApplicationEmail email)
    {
        mailer.sendMail(email.build());
    }
}
