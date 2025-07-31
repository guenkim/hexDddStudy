package geun.jpastudy.domain.manytomany;

import geun.jpastudy.domain.shared.BaseIdAndDate;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Student extends BaseIdAndDate {

    private String name;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();

    public static Student create(String name){
        Student student = new Student();
        student.name = name;
        return student;
    }
    public void addCourse(Course course) {
        courses.add(course);
        course.getStudents().add(this);
    }

}