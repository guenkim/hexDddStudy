package geun.hexdddstudy.adapter.webapi;

import geun.hexdddstudy.adapter.webapi.dto.MemberRegisterResponse;
import geun.hexdddstudy.application.member.provided.MemberRegister;
import geun.hexdddstudy.domain.member.Member;
import geun.hexdddstudy.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    @PostMapping("/api/members")
    public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberRegister.register(request);

        return MemberRegisterResponse.of(member);
    }
}
