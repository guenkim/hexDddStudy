package geun.hexdddstudy;

import geun.hexdddstudy.domain.member.MemberRegisterRequest;
import org.assertj.core.api.AssertProvider;
import org.springframework.test.json.JsonPathValueAssert;


import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertThatUtils {
    public static Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
        return value -> assertThat(value).isNotNull();
    }

    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(MemberRegisterRequest request) {
        return value -> assertThat(value).isEqualTo(request.email());
    }
}
