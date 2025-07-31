package geun.jpastudy.domain.onetomany_manytoone;

import geun.jpastudy.domain.shared.BaseIdAndDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseIdAndDate {

    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id" , nullable = false)
    private Author author;

    public static Book create(String title){
        Book book = new Book();
        book.title = title;
        return book;
    }

    public void setAuthor(Author author){
        this.author = author;
    }


}