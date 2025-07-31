package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetoone.Address;
import geun.jpastudy.domain.onetoone.Member_;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OneToOneTest {
    @Autowired
    Member_Repository memberRepository;
    @Autowired
    AddressRepository addressRepository;

    @Test
    public void testFindByName() {

        var preMember = memberRepository.findByName("test");
        assertThat(preMember).isEmpty();

        Address address = Address.create("Seoul", "123 Street");
        Member_ member = Member_.create("John Doe", address);
        memberRepository.save(member);

        Member_ found = memberRepository.findByName("John Doe").orElseThrow();
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
        assertThat(found.getAddress().getCity()).isEqualTo("Seoul");
        Long addressId = found.getAddress().getId();

        memberRepository.deleteById(found.getId());
        var returnMember = memberRepository.findById(found.getId());
        assertThat(returnMember).isEmpty();

        var returnAddress = addressRepository.findById(addressId);
        assertThat(returnAddress).isEmpty();
    }

    @Test
    void containName(){
        Address address = Address.create("Seoul", "123 Street");
        Member_ member = Member_.create("John Doe", address);
        memberRepository.save(member);

        List<Member_> memberS = memberRepository.containName("John Doe");
        assertThat(memberS.size()).isEqualTo(1);
        memberS.stream().forEach(eachMember -> {
            Address eachMemberAddressaddress = eachMember.getAddress();
            assertThat(eachMemberAddressaddress.getCity()).isEqualTo("Seoul");
        });
    }
}