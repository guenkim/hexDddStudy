package geun.hexdddstudy.domain.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {
    @Test
    void profile(){
        new Profile("");
        new Profile("1234");
        new Profile("kimgeun");
    }

    @Test
    void profileFail(){
        assertThatThrownBy(
        ()->{
            new Profile("dskfjdksjflkjdsfkjdslkjfkdsj");
        }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(
                ()->{
                    new Profile("A");
                }).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(
                ()->{
                    new Profile("프로필");
                }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url(){
        var geun = new Profile("kimgeun");
        assertThat(geun.url()).isEqualTo("@kimgeun");
    }

}