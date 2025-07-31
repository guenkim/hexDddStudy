package geun.jpastudy.application;

import geun.jpastudy.application.required.AuthorRepository;
import geun.jpastudy.application.required.Member_Repository;
import geun.jpastudy.application.required.StudentRepository;
import geun.jpastudy.domain.manytomany.Course;
import geun.jpastudy.domain.manytomany.Student;
import geun.jpastudy.domain.onetomany_manytoone.Author;
import geun.jpastudy.domain.onetomany_manytoone.Book;
import geun.jpastudy.domain.onetoone.Address;
import geun.jpastudy.domain.onetoone.Member_;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    @Autowired
    private Member_Repository memberRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public void saveMemberWithAddress() {
        Address address = Address.create("Seoul", "123 Street");
        Member_ member = Member_.create("John Doe", address);
        memberRepository.save(member);
    }

    @Transactional
    public void saveAuthorWithBooks() {
        Author author = Author.create("Jane Austen","sigongsa");

        Book book1 = Book.create("Pride and Prejudice");
        Book book2 = Book.create("Sense and Sensibility");

        author.addBook(book1);
        author.addBook(book2);

        authorRepository.save(author);
    }

    @Transactional
    public void saveStudentWithCourses() {
        Student student = Student.create("Alice");

        Course course1 = Course.create("Mathematics");
        Course course2 = Course.create("Physics");

        course1.addStudent(student);
        course2.addStudent(student);

        student.addCourse(course1);
        student.addCourse(course2);

        studentRepository.save(student);
    }

    // 추가: 이름으로 조회 예제
    @Transactional
    public Member_ findMemberByName(String name) throws Exception {
        return memberRepository.findByName(name).orElseThrow(()-> new Exception("그런 이름을 가진 멤버가 없다."));
    }

    @Transactional
    public Author findAuthorByName(String name) throws Exception {
        return authorRepository.findByName(name).orElseThrow(()-> new Exception("그런 이름을 가진 저자가 없다."));
    }

    @Transactional
    public Student findStudentByName(String name) throws Exception {
        return studentRepository.findByName(name).orElseThrow(()-> new Exception("그런 이름을 가진 학생이 없다."));
    }
}