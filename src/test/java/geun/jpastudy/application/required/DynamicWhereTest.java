package geun.jpastudy.application.required;

import com.querydsl.core.Tuple;
import geun.jpastudy.domain.onetomany_manytoone.Author;
import geun.jpastudy.domain.onetomany_manytoone.Book;
import geun.jpastudy.domain.onetomany_manytoone.IntegrationDTO2;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class DynamicWhereTest {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;

    @Autowired
    EntityManager em;

    /* 동적 조회 조건 함수
        * OneToMany 예제
            동적 조회 조건 적용
            entityGraph 적용
            INNER JOIN 처리
            N+1 현상 없음
            OneTooMany는 페이징 적용 안됨
     */
    @Transactional
    @Test
    void dynamicWhereOneToManyTest(){
        Author author = Author.create("geun", "sigongsa");
        Book book1 = Book.create("bmf1");
        Book book2 = Book.create("bmf2");

        author.addBook(book1);
        author.addBook(book2);
        book1.setAuthor(author);
        book2.setAuthor(author);
        authorRepository.save(author);

        Author author2 = Author.create("geun1", "sigongsa");
        author2.addBook(book1);
        author2.addBook(book2);
        book1.setAuthor(author2);
        book2.setAuthor(author2);
        authorRepository.save(author2);

        em.flush();
        em.clear();


        String authorName ="test";
        String title = "xxx";

        Specification<Author> spec = (root, query, cb) -> {
            // Book과 Author 조인
            //설정과 상관없이 여기 설정 된 JOIN TYPE으로 설정
            Join<Author,Book> authorJoin = root.join("books", JoinType.INNER);

            // 동적 조건 생성
            Specification<Author> finalSpec = Specification.where(null);

            // authorName 파라미터가 null이 아니고 비어 있지 않은 경우 조건 추가
            if (authorName != null && !authorName.trim().isEmpty()) {
                finalSpec = finalSpec.and((r, q, cbuilder) -> cbuilder.equal(root.get("name"), authorName));
            }

            // title 파라미터가 null이 아니고 비어 있지 않은 경우 조건 추가
            if (title != null && !title.trim().isEmpty()) {
                finalSpec = finalSpec.and((r, q, cbuilder) -> cbuilder.equal(authorJoin.get("title"), title));
            }

            // regdt를 기준으로 오름차순 정렬 추가
            query.orderBy(cb.asc(root.get("regdt")));

            // 중복 제거
            //query.distinct(true);

            return finalSpec.toPredicate(root, query, cb);
        };


        Pageable pageable = PageRequest.of(1, 1); // First page, 10 items per page
        Page<Author> results = authorRepository.findAll(spec,pageable);

        //long totalCount = result.getTotalElements(); //전체항목수
        //int size = result.getSize(); //페이지당 항목수
        //List<Author> authors = result.getContent(); //데이터 목록
        //int page = todoPage.getNumber(); //현재 페이지

        /**
        List<Author> results = authorRepository.findAll(spec);
        results.stream()
                .forEach(author1 -> {
                    author1.getBooks().stream().forEach(book -> {
                        System.out.println(book.getTitle());
                    });
                });


        // DATA 가공해서 DTO로 변환하기
        List<IntegrationDTO2> dataTransition = results.stream()
                .flatMap(author1 -> author1.getBooks().stream()
                        .map(book -> new IntegrationDTO2(book.getAuthor().getName(), book.getAuthor().getCompany(), book.getTitle())))
                .collect(Collectors.toList());


        dataTransition.stream().forEach(data-> System.out.println(data.getName() +"," + data.getCompany() + "," + data.getTitle()));
        **/
    }


    /* 동적 조회 조건 함수
        * ManyToOne 예제
            동적 조회 조건 적용
            entityGraph 적용
            INNER JOIN 처리
            N+1 현상 없음
            페이징 적용
     */
    @Transactional
    @Test
    void dynamicWhereManyToOneTest(){
        Author author = Author.create("geun", "sigongsa");
        Book book1 = Book.create("bmf1");
        Book book2 = Book.create("bmf2");

        author.addBook(book1);
        author.addBook(book2);
        book1.setAuthor(author);
        book2.setAuthor(author);
        authorRepository.save(author);

        Author author2 = Author.create("geun1", "sigongsa");
        author2.addBook(book1);
        author2.addBook(book2);
        book1.setAuthor(author2);
        book2.setAuthor(author2);
        authorRepository.save(author2);

        em.flush();
        em.clear();


        String authorName ="test";
        String title = "xxx";

        Specification<Book> spec = (root, query, cb) -> {
            // Book과 Author 조인
            //설정과 상관없이 여기 설정 된 JOIN TYPE으로 설정
            Join<Book,Author> authorJoin = root.join("author", JoinType.INNER);

            // 동적 조건 생성
            Specification<Book> finalSpec = Specification.where(null);

            // authorName 파라미터가 null이 아니고 비어 있지 않은 경우 조건 추가
            if (authorName != null && !authorName.trim().isEmpty()) {
                finalSpec = finalSpec.and((r, q, cbuilder) -> cbuilder.equal(authorJoin.get("name"), authorName));
            }

            // title 파라미터가 null이 아니고 비어 있지 않은 경우 조건 추가
            if (title != null && !title.trim().isEmpty()) {
                finalSpec = finalSpec.and((r, q, cbuilder) -> cbuilder.equal(root.get("title"), title));
            }

            // regdt를 기준으로 오름차순 정렬 추가
            query.orderBy(cb.asc(authorJoin.get("regdt")));

            // 중복 제거
            //query.distinct(true);

            return finalSpec.toPredicate(root, query, cb);
        };


        Pageable pageable = PageRequest.of(1, 1); // First page, 10 items per page
        Page<Book> results = bookRepository.findAll(spec,pageable);

        //long totalCount = result.getTotalElements(); //전체항목수
        //int size = result.getSize(); //페이지당 항목수
        //List<Author> authors = result.getContent(); //데이터 목록
        //int page = todoPage.getNumber(); //현재 페이지

        /**
         List<Author> results = authorRepository.findAll(spec);
         results.stream()
         .forEach(author1 -> {
         author1.getBooks().stream().forEach(book -> {
         System.out.println(book.getTitle());
         });
         });


         // DATA 가공해서 DTO로 변환하기
         List<IntegrationDTO2> dataTransition = results.stream()
         .flatMap(author1 -> author1.getBooks().stream()
         .map(book -> new IntegrationDTO2(book.getAuthor().getName(), book.getAuthor().getCompany(), book.getTitle())))
         .collect(Collectors.toList());


         dataTransition.stream().forEach(data-> System.out.println(data.getName() +"," + data.getCompany() + "," + data.getTitle()));
         **/
    }


    @BeforeEach
    @Transactional
    void setUp() {
        // 테스트 데이터 생성
        Author author1 = Author.create("test", "sigongsa");
        Book book1 = Book.create("xxx");
        Book book2 = Book.create("yyy");
        author1.addBook(book1);
        author1.addBook(book2);

        Author author2 = Author.create("test", "hanbit");
        Book book3 = Book.create("xxx");
        Book book4 = Book.create("zzz");
        author2.addBook(book3);
        author2.addBook(book4);

        authorRepository.save(author1);
        authorRepository.save(author2);
        em.flush();
        em.clear();
    }

    @Transactional
    @Test
    void findBooksByAuthorNameAndTitleTest_FirstPage() {
        // Given
        String authorName = "test";
        String title = "xxx";
        Pageable pageable = PageRequest.of(0, 1); // 첫 번째 페이지, 페이지당 1개 항목

        // When
        Page<Book> result = bookRepository.findBooksByAuthorNameAndTitle(authorName, title, pageable);

        // Then
        assertNotNull(result, "결과 페이지가 null이면 안 됩니다");
        assertEquals(2, result.getTotalElements(), "정확히 두 개의 Book이 조회되어야 합니다");
        assertEquals(1, result.getContent().size(), "페이지당 1개의 Book이 반환되어야 합니다");
        Book foundBook = result.getContent().get(0);
        assertEquals("xxx", foundBook.getTitle(), "Book 제목이 일치해야 합니다");
        assertEquals("test", foundBook.getAuthor().getName(), "Author 이름이 일치해야 합니다");
        assertNotNull(foundBook.getAuthor(), "Author가 null이면 안 됩니다");
        assertEquals("sigongsa", foundBook.getAuthor().getCompany(), "첫 번째 Author의 회사명이 일치해야 합니다");
    }

    @Transactional
    @Test
    void findBooksByAuthorNameAndTitleTest_SecondPage() {
        // Given
        String authorName = "test";
        String title = "xxx";
        Pageable pageable = PageRequest.of(1, 1); // 두 번째 페이지, 페이지당 1개 항목

        // When
        Page<Book> result = bookRepository.findBooksByAuthorNameAndTitle(authorName, title, pageable);

        // Then
        assertNotNull(result, "결과 페이지가 null이면 안 됩니다");
        assertEquals(2, result.getTotalElements(), "정확히 두 개의 Book이 조회되어야 합니다");
        assertEquals(1, result.getContent().size(), "페이지당 1개의 Book이 반환되어야 합니다");
        Book foundBook = result.getContent().get(0);
        assertEquals("xxx", foundBook.getTitle(), "Book 제목이 일치해야 합니다");
        assertEquals("test", foundBook.getAuthor().getName(), "Author 이름이 일치해야 합니다");
        assertNotNull(foundBook.getAuthor(), "Author가 null이면 안 됩니다");
        assertEquals("hanbit", foundBook.getAuthor().getCompany(), "두 번째 Author의 회사명이 일치해야 합니다");
    }

    @Transactional
    @Test
    void findBooksByAuthorNameAndTitleTest_NullConditions() {
        // Given
        String authorName = null;
        String title = null;
        Pageable pageable = PageRequest.of(0, 2); // 첫 번째 페이지, 페이지당 2개 항목

        // When
        Page<Book> result = bookRepository.findBooksByAuthorNameAndTitle(authorName, title, pageable);

        // Then
        assertNotNull(result, "결과 페이지가 null이면 안 됩니다");
        assertEquals(4, result.getTotalElements(), "모든 Book이 조회되어야 합니다 (4개)");
        assertEquals(2, result.getContent().size(), "페이지당 2개의 Book이 반환되어야 합니다");
        assertNotNull(result.getContent().get(0).getAuthor(), "Author가 null이면 안 됩니다");
    }

}
