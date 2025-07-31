package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetoone.Address;
import geun.jpastudy.domain.onetoone.Member_;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class EntityGraphTest {
    @Autowired
    Member_Repository memberRepository;
    /**
     * @EntityGraph(attributePaths={"address"})를 설정해야 join 쿼리가 나감
     * @JoinColumn 에서 nullable=false(inner join) , nullable=true(left join)
     */
    @Test
    void findEntityGraphById(){
        Address address = Address.create("Seoul", "123 Street");
        Member_ member = Member_.create("John Doe", address);
        memberRepository.save(member);

        memberRepository.findEntityGraphById(member.getId());
    }
}
