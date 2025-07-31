package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetoone.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
