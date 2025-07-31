package geun.hexdddstudy.application.member.provided;

import geun.hexdddstudy.domain.member.Member;
import geun.hexdddstudy.domain.member.MemberInfoUpdateRequest;
import geun.hexdddstudy.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest memberRegisterRequest);

    Member activate(Long memberId);
    Member deActivate(Long memberId);
    Member updateInfo(Long memberId , @Valid MemberInfoUpdateRequest memberInfoUpdateRequest);


}
