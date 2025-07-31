package geun.jpastudy.domain.onetoone;

import geun.jpastudy.domain.shared.BaseIdAndDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseIdAndDate {

    private String city;
    private String street;

    @OneToOne(mappedBy = "address")
    private Member_ member;

    public static Address create(String city , String street){
        Address address = new Address();
        address.city = city;
        address.street = street;
        return address;
    }
}