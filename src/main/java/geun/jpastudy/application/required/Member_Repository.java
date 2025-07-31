package geun.jpastudy.application.required;

import com.querydsl.jpa.impl.JPAQueryFactory;
import geun.jpastudy.domain.onetoone.Member_;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface Member_Repository extends JpaRepository<Member_,Long> , Member_RepoEx {
    Optional<Member_> findByName(String name);
    List<Member_> containName(String name);


    @EntityGraph(attributePaths={"address"})
    Optional<Member_> findEntityGraphById(Long memberId);
}
