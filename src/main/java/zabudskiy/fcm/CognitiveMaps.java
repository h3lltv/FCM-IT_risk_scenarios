package zabudskiy.fcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class CognitiveMaps {

    public static void main(String[] args) throws Throwable {
        SpringApplication.run(CognitiveMaps.class, args);
    }
}
