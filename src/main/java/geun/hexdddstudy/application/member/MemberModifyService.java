package geun.hexdddstudy.application.member;

import geun.hexdddstudy.application.member.provided.MemberFinder;
import geun.hexdddstudy.application.member.provided.MemberRegister;
import geun.hexdddstudy.application.member.required.EmailSender;
import geun.hexdddstudy.application.member.required.MemberRepository;
import geun.hexdddstudy.domain.member.*;
import geun.hexdddstudy.domain.shared.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {
    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Member register(MemberRegisterRequest memberRegisterRequest) {
        checkDuplicateEmail(memberRegisterRequest);
        Member member = Member.register(memberRegisterRequest, passwordEncoder);
        memberRepository.save(member);
        sendWelcomeEmail(member);
        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId);
        member.activate();
        return memberRepository.save(member);
    }

    @Override
    public Member deActivate(Long memberId) {
        Member member = memberFinder.find(memberId);
        member.deactivate();
        return memberRepository.save(member);
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest memberInfoUpdateRequest) {
        Member member = memberFinder.find(memberId);
        checkDuplicateProfile(member,memberInfoUpdateRequest.profileAddress());
        member.updateInfo(memberInfoUpdateRequest);
        return memberRepository.save(member);
    }

    private void checkDuplicateEmail(MemberRegisterRequest memberRegisterRequest) {
        if(memberRepository.findByEmail(new Email(memberRegisterRequest.email())).isPresent()){
            throw new DuplicateEmailException("이미 사용중인 이메일입니다:"+memberRegisterRequest.email());
        }
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(),"등록을 완료해주세요","아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateProfile(Member member, String profileAddress) {
        if (profileAddress.isEmpty()) return;

        Profile currentProfile = member.getDetail().getProfile();
        if (currentProfile != null && currentProfile.address().equals(profileAddress)) return;

        if (memberRepository.findByProfile(new Profile(profileAddress)).isPresent()) {
            throw new DuplicateProfileException("이미 존재하는 프로필 주소입니다: " + profileAddress);
        }

    }
}
