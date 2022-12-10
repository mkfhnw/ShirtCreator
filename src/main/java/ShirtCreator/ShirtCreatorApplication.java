package ShirtCreator;

import ShirtCreator.Persistence.Order;
import ShirtCreator.Persistence.OrderRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShirtCreatorApplication {

    @Autowired
    private OrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShirtCreatorApplication.class, args);
    }

    @PostConstruct
    public void createTestData() {

        Order o1 = new Order(1, 1, 10);
        Order o2 = new Order(2, 2, 20);
        Order o3 = new Order(1, 3, 30);
        Order o4 = new Order(3, 4, 40);

        orderRepository.save(o1);
        orderRepository.save(o2);
        orderRepository.save(o3);
        orderRepository.save(o4);

    }
}
