//Define a Course class with attributes courseName and duration.
public class Course {
    String courseName;
    int duration;

    public Course(String courseName, int duration) {
        this.courseName = courseName;
        this.duration = duration;
    }

    public String toString() {
        return courseName + " (" + duration + " months)";
    }
}

//Define a Student class with attributes name and a reference to Course.
public class Student {
    String name;
    Course course;

    public Student(String name, Course course) {
        this.name = name;
        this.course = course;
    }

    public void showDetails() {
        System.out.println("Name: " + name + ", Course: " + course);
    }
}

//Use Java-based configuration (@Configuration and @Bean) to configure the beans.
@Configuration
public class AppConfig {

    @Bean
    public Course course() {
        return new Course("Java Spring", 3);
    }

    @Bean
    public Student student() {
        return new Student("Aman", course());
    }
}
//Load the Spring context in the main method and print student details.
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class MainApp {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        Student student = context.getBean(Student.class);
        student.showDetails();
        context.close();
    }
}
