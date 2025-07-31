package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetomany_manytoone.Author;
import geun.jpastudy.domain.onetomany_manytoone.Book;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ManyToOneTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    EntityManager em;

    @Test
    void manyToOneTest(){
        Author author = Author.create("geun","sigongsa");
        Book book1 = Book.create("book1");
        Book book2 = Book.create("book2");

        book1.setAuthor(author);
        book2.setAuthor(author);

        author.addBook(book1);
        author.addBook(book2);

        authorRepository.save(author);
        em.flush();
        em.clear();

        List<Book> books = authorRepository.findById(author.getId()).get().getBooks();
        assertThat(books.size()).isEqualTo(2);
    }
}
