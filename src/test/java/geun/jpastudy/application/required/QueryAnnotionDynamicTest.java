package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetomany_manytoone.Author;
import geun.jpastudy.domain.onetomany_manytoone.Book;
import geun.jpastudy.domain.onetomany_manytoone.IntegrationDTO2;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class QueryAnnotionDynamicTest {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    EntityManager em;
    @BeforeEach
    @Transactional
    void setUp() {
        // 테스트 데이터 생성
        Author author1 = Author.create("test", "sigongsa");
        Book book1 = Book.create("xxx");
        Book book2 = Book.create("yyy");
        author1.addBook(book1);
        author1.addBook(book2);

        Author author2 = Author.create("test2", "hanbit");
        Book book3 = Book.create("xxx");
        Book book4 = Book.create("zzz");
        author2.addBook(book3);
        author2.addBook(book4);

        authorRepository.save(author1);
        authorRepository.save(author2);
        em.flush();
        em.clear();
    }

    /* 동적 조회 조건 함수
           * ManyToOne 예제
            동적 조회 조건 적용
            JOIN FETCH 처리 (INNER JOIN)
            N+1 현상 없음
            페이징 적용
     */

    @Transactional
    @Test
    void queryAnnotationDynamicFirstPage() {
        // Given
        String authorName = "test";
        String title = "xxx";
        Pageable pageable = PageRequest.of(0, 1); // 첫 번째 페이지, 페이지당 1개 항목

        // When
        Page<Book> result = bookRepository.findBooksByAuthorNameAndTitle(authorName, title, pageable);

        // Then
        assertNotNull(result, "결과 페이지가 null이면 안 됩니다");
        assertEquals(1, result.getTotalElements(), "정확히 1개의 Book이 조회되어야 합니다");
        assertEquals(1, result.getContent().size(), "페이지당 1개의 Book이 반환되어야 합니다");
        Book foundBook = result.getContent().get(0);
        assertEquals("xxx", foundBook.getTitle(), "Book 제목이 일치해야 합니다");
        assertEquals("test", foundBook.getAuthor().getName(), "Author 이름이 일치해야 합니다");
        assertNotNull(foundBook.getAuthor(), "Author가 null이면 안 됩니다");
        assertEquals("sigongsa", foundBook.getAuthor().getCompany(), "첫 번째 Author의 회사명이 일치해야 합니다");

        //page 정보
        long totalCount = result.getTotalElements(); //전체항목수
        int size = result.getSize(); //페이지당 항목수
        List<Book> authors = result.getContent(); //데이터 목록
        int page = result.getNumber(); //현재 페이지


        // DATA 가공해서 DTO로 변환하기
        List<IntegrationDTO2> dataTransition = result.stream()
                .map(book -> new IntegrationDTO2(
                                book.getAuthor().getName(),
                                book.getAuthor().getCompany(),
                                book.getTitle()
                        )
                ).collect(Collectors.toList());

        dataTransition.stream().forEach(data-> System.out.println(data.toString()));
    }

    @Transactional
    @Test
    void queryAnnotationDynamicSecondPage() {
        // Given
        String authorName = "test";
        String title = "xxx";
        Pageable pageable = PageRequest.of(1, 1); // 두 번째 페이지, 페이지당 1개 항목

        // When
        Page<Book> result = bookRepository.findBooksByAuthorNameAndTitle(authorName, title, pageable);

        // Then
        assertThat(result.getContent().size()).isEqualTo(0);

    }

    @Transactional
    @Test
    void queryAnnotationDynamicNullConditions() {
        // Given
        String authorName = null;
        String title = "";
        Pageable pageable = PageRequest.of(0, 2); // 첫 번째 페이지, 페이지당 2개 항목

        // When
        Page<Book> result = bookRepository.findBooksByAuthorNameAndTitle(authorName, title, pageable);

        // Then
        assertNotNull(result, "결과 페이지가 null이면 안 됩니다");
        assertEquals(4, result.getTotalElements(), "모든 Book이 조회되어야 합니다 (4개)");
        assertEquals(2, result.getContent().size(), "페이지당 2개의 Book이 반환되어야 합니다");
        assertNotNull(result.getContent().get(0).getAuthor(), "Author가 null이면 안 됩니다");

        // DATA 가공해서 DTO로 변환하기
        List<IntegrationDTO2> dataTransition = result.stream()
                .map(book -> new IntegrationDTO2(
                                book.getAuthor().getName(),
                                book.getAuthor().getCompany(),
                                book.getTitle()
                        )
                ).collect(Collectors.toList());

        dataTransition.stream().forEach(data-> System.out.println(data.toString()));
    }
}
