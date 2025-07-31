package geun.hexdddstudy.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static geun.hexdddstudy.domain.member.MemberFixture.createMemberRegisterRequest;
import static geun.hexdddstudy.domain.member.MemberFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        this.passwordEncoder = createPasswordEncoder();
        member = Member.register(createMemberRegisterRequest(),passwordEncoder);
    }

    @Test
    void registerMember(){
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void active(){
        assertThat(member.getDetail().getActivatedAt()).isNull();
        member.activate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
        assertThat(member.getDetail().getActivatedAt()).isNotNull();assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }

    @Test
    void activeFail(){
        member.activate();
        assertThatThrownBy(
        ()->{
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void deactive(){
        member.activate();
        member.deactivate();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void deactiveFail(){
        assertThatThrownBy(
        ()->{
            member.deactivate();
        }).isInstanceOf(IllegalStateException.class);
    }
    
    @Test
    void verifyPassword(){
        assertThat(member.verifyPassword("12345678",passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("123456789",passwordEncoder)).isFalse();
    }

    @Test
    void changePassword(){
        member.changePassword("12345678",passwordEncoder);
        assertThat(member.verifyPassword("12345678",passwordEncoder)).isTrue();
    }

    @Test
    void isActive(){
        assertThat(member.isActive()).isFalse();
        member.activate();
        assertThat(member.isActive()).isTrue();
        member.deactivate();
        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail(){
        assertThatThrownBy(
        ()->{
            member.register(createMemberRegisterRequest("invalid email"),passwordEncoder);
        }).isInstanceOf(IllegalArgumentException.class);
        member.register(createMemberRegisterRequest(),passwordEncoder);
    }

    @Test
    void updateInfo() {
        member.activate();

        var request = new MemberInfoUpdateRequest("Leo", "toby100", "자기소개");
        member.updateInfo(request);

        assertThat(member.getNickname()).isEqualTo(request.nickname());
        assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        assertThat(member.getDetail().getIntroduction()).isEqualTo(request.introduction());
    }

    @Test
    void updateInfoFail() {
        assertThatThrownBy(() -> {
            var request = new MemberInfoUpdateRequest("Leo", "toby100", "자기소개");
            member.updateInfo(request);
        }).isInstanceOf(IllegalStateException.class);
    }



}