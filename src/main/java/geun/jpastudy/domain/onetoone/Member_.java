package geun.jpastudy.domain.onetoone;

import geun.jpastudy.domain.shared.BaseIdAndDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Member_ extends BaseIdAndDate {

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id" , nullable = false)
    private Address address;

    public static Member_ create(String name,Address address){
        Member_ member = new Member_();
        member.address = address;
        member.name = name;
        return member;
    }

    public void updateName(String name){
        this.name = name;
    }
}
