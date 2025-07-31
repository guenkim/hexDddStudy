package geun.hexdddstudy.application.member.provided;

import geun.hexdddstudy.domain.member.Member;

public interface MemberFinder {
    Member find(Long memberId);
}
