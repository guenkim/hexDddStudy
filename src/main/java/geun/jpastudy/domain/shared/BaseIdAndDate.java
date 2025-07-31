package geun.jpastudy.domain.shared;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@ToString
@MappedSuperclass
public abstract class BaseIdAndDate extends AbstractEntity implements Serializable {

    private static final long serialVersionID = 1L;

    @PrePersist
    private void beforePersist() {
        regdt = LocalDateTime.now();
    }

    @PreUpdate
    private void beforeUpdate(){
        moddt = LocalDateTime.now();
    }


    @CreatedDate
    @Column(name = "regdt", updatable = false)
    protected LocalDateTime regdt;

    @LastModifiedDate
    @Column(name = "moddt")
    protected LocalDateTime moddt;

}

