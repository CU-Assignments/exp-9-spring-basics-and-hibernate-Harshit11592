import jakarta.persistence.*;

@Entity
public class Student {
    @Id @GeneratedValue
    private int id;
    private String name;
    private int age;

    public Student() {}
    public Student(String name, int age) { this.name = name; this.age = age; }
    public String toString() { return id + " " + name + " " + age; }
}
<hibernate-configuration>
 <session-factory>
  <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
  <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/hibernate_db</property>
  <property name="hibernate.connection.username">root</property>
  <property name="hibernate.connection.password">your_password</property>
  <property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
  <property name="hbm2ddl.auto">update</property>
  <property name="show_sql">true</property>
  <mapping class="Student"/>
 </session-factory>
</hibernate-configuration>
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();

        // CREATE
        Student s1 = new Student("Ivory", 22);
        session.save(s1);

        // READ
        Student s = session.get(Student.class, 1);
        System.out.println("Read: " + s);

        // UPDATE
        s.setName("Ivory Updated");
        session.update(s);

        // DELETE
        session.delete(s);

        tx.commit();
        session.close();
        sf.close();
    }
}
