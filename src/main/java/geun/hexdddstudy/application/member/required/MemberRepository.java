package geun.hexdddstudy.application.member.required;

import geun.hexdddstudy.domain.member.Member;
import geun.hexdddstudy.domain.member.Profile;
import geun.hexdddstudy.domain.shared.Email;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member,Long> {
    Member save(Member member);
    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);

    @Query("select m from Member m where m.detail.profile = :profile")
    Optional<Member> findByProfile(Profile profile);
}
