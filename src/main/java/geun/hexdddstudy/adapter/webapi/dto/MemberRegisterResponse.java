package geun.hexdddstudy.adapter.webapi.dto;


import geun.hexdddstudy.domain.member.Member;

public record MemberRegisterResponse(Long memberId, String email) {
    public static MemberRegisterResponse of(Member member) {
        return new MemberRegisterResponse(member.getId(), member.getEmail().address());
    }
}
