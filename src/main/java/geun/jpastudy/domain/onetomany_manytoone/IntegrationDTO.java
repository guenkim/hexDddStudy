package geun.jpastudy.domain.onetomany_manytoone;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;


public interface IntegrationDTO {

    Long getAuthorId();
    String getName();

    String getCompany();

    LocalDateTime getAuthorRegdt();
    LocalDateTime getAuthorModdt();

    Long getBookId();

    String getTitle();

    LocalDateTime getBookRegdt();
    LocalDateTime getBookModdt();

}
