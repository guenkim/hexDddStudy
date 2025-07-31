package geun.hexdddstudy.domain.member;

import geun.hexdddstudy.domain.AbstractEntity;
import geun.hexdddstudy.domain.shared.Email;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;


@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends AbstractEntity {
    @NaturalId
    private Email email;
    private String nickname;
    private String passwordHash;
    private MemberStatus status;
    private MemberDetail detail;

    public static Member register(MemberRegisterRequest createRequest , PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.email = new Email(createRequest.email());
        member.nickname = createRequest.nickname();
        member.passwordHash = passwordEncoder.encode(createRequest.password());
        member.status = MemberStatus.PENDING;
        member.detail = MemberDetail.create();
        return member;
    }

    public void activate(){
        state(status==MemberStatus.PENDING,"PENDING 상태가 아닙니다.");
        this.status = MemberStatus.ACTIVE;
        this.detail.activate();
    }

    public void deactivate() {
        state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

        this.status = MemberStatus.DEACTIVATED;
        this.detail.deActivate();
    }

    public boolean verifyPassword(String password,PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(password,this.passwordHash);
    }

    public void updateInfo(MemberInfoUpdateRequest updateRequest){
        state(getStatus()==MemberStatus.ACTIVE,"등록 완료 상태가 아니면 정보를 수정할 수 없습니다.");
        this.nickname = updateRequest.nickname();
        this.detail.updateInfo(updateRequest);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive(){
        return this.status == MemberStatus.ACTIVE;
    }


}
