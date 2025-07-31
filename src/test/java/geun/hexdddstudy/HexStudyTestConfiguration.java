package geun.hexdddstudy;

import geun.hexdddstudy.application.member.required.EmailSender;
import geun.hexdddstudy.domain.member.Member;
import geun.hexdddstudy.domain.member.MemberFixture;
import geun.hexdddstudy.domain.member.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class HexStudyTestConfiguration {
    @Bean
    public EmailSender emailSender(){
        return ((email, subject, body) -> System.out.println("sending email :"+email));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){return MemberFixture.createPasswordEncoder();}
}
