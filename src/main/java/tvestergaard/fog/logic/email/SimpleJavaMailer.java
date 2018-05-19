package tvestergaard.fog.logic.email;

import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;
import tvestergaard.fog.data.customers.Customer;

public class SimpleJavaMailer implements ApplicationMailer
{

    /**
     * The simple java mailer that actually sends the email.
     */
    private final Mailer        mailer;
    private final EmailTemplate template;

    private static final String HOST = "smtp.gmail.com";
    private static final int    PORT = 587;
    private static final String USER = "fog.carporte@gmail.com";
    private static final String PASS = "m48yXLaX";

    /**
     * Creates a new {@link SimpleJavaMailer}.
     */
    public SimpleJavaMailer(EmailTemplate template)
    {
        this.mailer = new Mailer(HOST, PORT, USER, PASS, TransportStrategy.SMTP_TLS);
        this.template = template;
    }

    /**
     * Sends the provided application email.
     *
     * @param email The email to send.
     */
    @Override public void send(ApplicationEmail email)
    {
        Customer recipient = email.getRecipient();

        mailer.sendMail(new EmailBuilder()
                .to(recipient.getName(), recipient.getEmail())
                .subject(email.getSubject())
                .from("Fog carporte", USER)
                .textHTML(createHTML(email))
                .build());
    }

    private String createHTML(ApplicationEmail email)
    {
        return template.render(email.getSubject(), email.getHtmlContents());
    }
}
