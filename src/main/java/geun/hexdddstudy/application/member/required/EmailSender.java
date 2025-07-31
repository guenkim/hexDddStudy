package geun.hexdddstudy.application.member.required;

import geun.hexdddstudy.domain.shared.Email;

public interface EmailSender {
    void send(Email email, String subject, String body);
}
