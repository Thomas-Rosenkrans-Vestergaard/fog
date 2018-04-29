package tvestergaard.fog.logic.email;

import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

public class ApplicationMailer
{
    private final Mailer mailer;

    public ApplicationMailer()
    {
        this.mailer = new Mailer("smtp.gmail.com", 587, "fog.carporte@gmail.com", "m48yXLaX", TransportStrategy.SMTP_TLS);
    }

    public void send(ApplicationEmail email)
    {
        this.mailer.sendMail(email.build());
    }
}
