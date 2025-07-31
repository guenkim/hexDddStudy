package geun.jpastudy.application.required;

import geun.jpastudy.domain.onetomany_manytoone.Author;
import geun.jpastudy.domain.onetomany_manytoone.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book,Long>, JpaSpecificationExecutor<Book> {

    @EntityGraph(attributePaths={"author"})
    Page<Book> findAll(Specification specification , Pageable pageable);

    @Query(value = "SELECT DISTINCT b FROM Book b JOIN FETCH b.author a " +
            "WHERE (:authorName IS NULL OR TRIM(:authorName) = '' OR a.name = :authorName) " +
            "AND (:title IS NULL OR TRIM(:title) = '' OR b.title = :title) " +
            "order by b.author.regdt asc "
            ,countQuery = "SELECT COUNT(DISTINCT b) FROM Book b JOIN b.author a " +
                    "WHERE (:authorName IS NULL OR TRIM(:authorName) = '' OR a.name = :authorName) " +
                    "AND (:title IS NULL OR TRIM(:title) = '' OR b.title = :title)"
    )
    Page<Book> findBooksByAuthorNameAndTitle(@Param("authorName") String authorName,
                                             @Param("title") String title,
                                             Pageable pageable);
}
