package geun.jpastudy.application.required;

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

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class Specification_dynamicTest {

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

    /*
        ManyToOne 예제
        동적 조회 조건 적용
        entityGraph 적용
        JOIN FETCH 처리 (INNER JOIN)
        N+1 현상 없음
        페이징 적용

        * 이슈 : JOIN 절이 두번 반복되어 나온다.
        select
            b1_0.id,
            b1_0.author_id,
            a2_0.id,
            a2_0.company,
            a2_0.moddt,
            a2_0.name,
            a2_0.regdt,
            b1_0.moddt,
            b1_0.regdt,
            b1_0.title
        from
            book b1_0
        join
            author a1_0
                on a1_0.id=b1_0.author_id
        join
            author a2_0
                on a2_0.id=b1_0.author_id
        where
            a1_0.name='test'
            and b1_0.title='xxx'
        order by
            a1_0.regdt
        offset
            1 rows
        fetch
            first 1 rows only
     */
    @Transactional
    @Test
    void dynamicConditionWithSpecificTest(){

        String authorName ="";
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

        //page 정보
        long totalCount = results.getTotalElements(); //전체항목수
        int size = results.getSize(); //페이지당 항목수
        List<Book> authors = results.getContent(); //데이터 목록
        int page = results.getNumber(); //현재 페이지

        assertThat(totalCount).isEqualTo(2);
        assertThat(size).isEqualTo(1);
        assertThat(authors.size()).isEqualTo(1);
        assertThat(page).isEqualTo(1);


         // DATA 가공해서 DTO로 변환하기
         List<IntegrationDTO2> dataTransition = results.stream()
         .map(book -> new IntegrationDTO2(
                                                    book.getAuthor().getName(),
                                                    book.getAuthor().getCompany(),
                                                    book.getTitle()
                                                )
         ).collect(Collectors.toList());

         dataTransition.stream().forEach(data-> System.out.println(data.toString()));

    }
}
