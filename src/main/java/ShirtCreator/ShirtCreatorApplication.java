package ShirtCreator;

import ShirtCreator.Persistence.Order;
import ShirtCreator.Persistence.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShirtCreatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShirtCreatorApplication.class, args);
    }

}
