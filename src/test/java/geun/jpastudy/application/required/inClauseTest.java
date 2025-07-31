package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetomany_manytoone.Author;
import geun.jpastudy.domain.onetomany_manytoone.Book;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class inClauseTest {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void findAuthorByCompanies(){
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

        Collection<String> companies = new ArrayList<>();
        companies.add("sigongsa");
        companies.add("sigongsa2");
        companies.add("sigongsa3");

        List<Author> authors = authorRepository.findAuthorByCompanies(companies);
        assertThat(authors.size()).isEqualTo(1);

    }
}
