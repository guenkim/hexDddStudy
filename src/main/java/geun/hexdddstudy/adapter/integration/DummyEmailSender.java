package geun.hexdddstudy.adapter.integration;

import geun.hexdddstudy.application.member.required.EmailSender;
import geun.hexdddstudy.domain.shared.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
@Fallback
public class DummyEmailSender implements EmailSender {

    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("DummyEmailSender send email: " + email);
    }
}
