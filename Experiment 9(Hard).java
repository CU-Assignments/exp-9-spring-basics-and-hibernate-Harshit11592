import jakarta.persistence.*;

@Entity
public class Account {
    @Id @GeneratedValue
    private int id;
    private String name;
    private double balance;

    public Account() {}
    public Account(String name, double balance) {
        this.name = name; this.balance = balance;
    }

    // Getters/setters...
}
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id @GeneratedValue
    private int id;
    private int fromAcc;
    private int toAcc;
    private double amount;
    private LocalDateTime time = LocalDateTime.now();

    public Transaction() {}
    public Transaction(int from, int to, double amt) {
        this.fromAcc = from; this.toAcc = to; this.amount = amt;
    }
}
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

@Configuration @EnableTransactionManagement
@ComponentScan("com.example")
public class AppConfig {

    @Bean
    public DataSource ds() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/bank_db");
        ds.setUsername("root"); ds.setPassword("password");
        return ds;
    }

    @Bean
    public LocalSessionFactoryBean sf() {
        LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
        sf.setDataSource(ds());
        sf.setPackagesToScan("com.example");
        sf.getHibernateProperties().put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        sf.getHibernateProperties().put("hibernate.hbm2ddl.auto", "update");
        sf.getHibernateProperties().put("show_sql", "true");
        return sf;
    }

    @Bean
    public HibernateTransactionManager txManager(SessionFactory sf) {
        return new HibernateTransactionManager(sf);
    }
}
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        var bank = ctx.getBean(BankService.class);

        try {
            bank.transfer(1, 2, 500); // ✅ Successful transfer
            bank.transfer(2, 1, 99999); // ❌ Should rollback
        } catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }

        ctx.close();
    }
}
