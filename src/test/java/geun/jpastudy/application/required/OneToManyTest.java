package geun.jpastudy.application.required;


import geun.hexdddstudy.AssertThatUtils;
import geun.jpastudy.domain.onetomany_manytoone.Author;
import geun.jpastudy.domain.onetomany_manytoone.Book;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OneToManyTest {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    EntityManager entityManager;

    @Test
    void oneToManyTest(){
        Author author = Author.create("Jane Austen","sigongsa");

        Book book1 = Book.create("Pride and Prejudice");
        Book book2 = Book.create("Sense and Sensibility");

        author.addBook(book1);
        author.addBook(book2);

        Author rtnAuthor = authorRepository.save(author);
        entityManager.flush();
        entityManager.clear();

        var findAuthor = authorRepository.findById(rtnAuthor.getId());
        assertThat(findAuthor).isPresent();
        List<Book> books = findAuthor.get().getBooks();
        assertThat(books.size()).isEqualTo(2);

    }
}
