package geun.hexdddstudy.domain.shared;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void equality(){
        var email1 = new Email("kimgeun@email.com");
        var email2 = new Email("kimgeun@email.com");
        assertThat(email1).isEqualTo(email2);
    }
}