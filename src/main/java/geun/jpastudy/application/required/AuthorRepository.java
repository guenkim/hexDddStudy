package geun.jpastudy.application.required;

import com.querydsl.core.Tuple;
import geun.jpastudy.domain.onetomany_manytoone.Author;
import geun.jpastudy.domain.onetomany_manytoone.Book;
import geun.jpastudy.domain.onetomany_manytoone.IntegrationDTO;
import geun.jpastudy.domain.onetomany_manytoone.IntegrationDTO2;
import geun.jpastudy.domain.onetoone.Member_;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Long>, JpaSpecificationExecutor<Author> {

    Optional<Author> findByName(String name);

    @Query("select author from Author author where author.company= :company")
    Author findAuthorLazyByCompany(@Param("company") String company);

    @Query("select author from Author author join fetch author.books where author.company=:company")
    Author findAuthorEagerByCompany(@Param("company") String company);

    @Query("select author from Author author where author.company in :companies")
    List<Author> findAuthorByCompanies(@Param("companies") Collection<String> companies);

    @Query(value = "select " +
            " author.id as authorId, " +
            " author.name as name,"+
            " author.company as company,"+
            " author.regdt as authorRegdt,"+
            " author.moddt as authorModdt,"+
            " book.id as bookId,"+
            " book.title as title ,"+
            " book.moddt as bookRegdt ,"+
            " book.regdt as bookModdt"+
            " from Author author inner join Book book on  author.id =  book.author_id", nativeQuery = true)
    List<IntegrationDTO> findNativeQueryByAuthors();


    @EntityGraph(attributePaths={"books"})
    List<Author> findAll(Specification specification);

}
