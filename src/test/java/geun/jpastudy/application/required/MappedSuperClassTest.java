package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetoone.Address;
import geun.jpastudy.domain.onetoone.Member_;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MappedSuperClassTest {
    @Autowired
    Member_Repository member_repository;
    @Autowired
    EntityManager em;


    @Test
    @Transactional
    void MappedSuperClassTest(){
        //given
        Address address = Address.create("seoul", "mangwornro");
        Member_ member = Member_.create("geun", address);

        //when
        member_repository.save(member);

        //then
        assertThat(member.getRegdt()).isNotNull();
        assertThat(member.getModdt()).isNull();
        em.flush();
        em.clear();

        //given
        Member_ rtnMember = member_repository.findById(member.getId()).orElseThrow();
        rtnMember.updateName("kimgeun");

        //when
        member_repository.save(rtnMember);

        em.flush();
        em.clear();

        //then
        assertThat(rtnMember.getModdt()).isNotNull();

    }
}
