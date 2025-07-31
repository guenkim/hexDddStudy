package geun.jpastudy.domain.manytomany;

import geun.jpastudy.domain.shared.AbstractEntity;
import geun.jpastudy.domain.shared.BaseIdAndDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseIdAndDate {
    private String title;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    public static Course create(String title){
        Course course = new Course();
        course.title = title;
        return course;
    }

    public void addStudent(Student student){
        students.add(student);
        student.addCourse(this);
    }

}
