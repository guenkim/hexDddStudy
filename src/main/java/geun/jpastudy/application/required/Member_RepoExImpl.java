package geun.jpastudy.application.required;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import geun.jpastudy.domain.onetoone.Member_;
import geun.jpastudy.domain.onetoone.QAddress;
import geun.jpastudy.domain.onetoone.QMember_;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static geun.jpastudy.domain.onetoone.QAddress.address;
import static geun.jpastudy.domain.onetoone.QMember_.member_;


public class Member_RepoExImpl extends QuerydslRepositorySupport implements Member_RepoEx {
    private JPAQueryFactory jqf;
    private EntityManager em;

    public Member_RepoExImpl() {
        super(Member_.class);
    }

    @Override
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
        em = entityManager;
        jqf = new JPAQueryFactory(entityManager);
    }

    public List<Member_> containName(String name) {
        return jqf.selectFrom(member_)
                .join(address).on(member_.address.id.eq(address.id))
                .where(member_.name.contains(name))
                .fetch();
    }
}
