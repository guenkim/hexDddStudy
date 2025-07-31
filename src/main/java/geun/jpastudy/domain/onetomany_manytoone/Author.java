package geun.jpastudy.domain.onetomany_manytoone;

import geun.jpastudy.domain.shared.BaseIdAndDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Author extends BaseIdAndDate {

    private String name;

    private String company;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    public static Author create(String name,String company){
        Author author = new Author();
        author.name = name;
        author.company = company;
        return author;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }
}
