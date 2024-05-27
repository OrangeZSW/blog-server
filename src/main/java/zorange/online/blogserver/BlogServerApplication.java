package zorange.online.blogserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zorange.online.blogserver.utils.Orange;

@SpringBootApplication
public class BlogServerApplication {
    public static void main(String[] args) {

        SpringApplication.run(BlogServerApplication.class, args);
        new Orange();
    }

}
