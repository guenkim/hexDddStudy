package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetomany_manytoone.Author;
import geun.jpastudy.domain.onetomany_manytoone.Book;
import geun.jpastudy.domain.onetomany_manytoone.IntegrationDTO;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class NativeQueryAnnotaion {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void findNativeQueryByAuthors(){
        Author author = Author.create("geun", "sigongsa");
        Book book1 = Book.create("bmf1");
        Book book2 = Book.create("bmf2");

        author.addBook(book1);
        author.addBook(book2);
        book1.setAuthor(author);
        book2.setAuthor(author);

        authorRepository.save(author);
        em.flush();
        em.clear();

        List<IntegrationDTO> authors = authorRepository.findNativeQueryByAuthors();
        assertThat(authors.size()).isEqualTo(2);
    }

}
