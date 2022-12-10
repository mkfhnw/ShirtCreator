package com.example.shirtcreator;

import Models.Order;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShirtCreatorApplication {

    /*@Autowired
    TODO: Add TestData
    private OrderRepository orderRepository;
    */

    public static void main(String[] args) {
        SpringApplication.run(ShirtCreatorApplication.class, args);
    }

    /* @PostConstruct
    TODO: Add TestData
    public void createTestData() {

        orderRepository.addOrder(new Order(1, 1, 10));
        orderRepository.addOrder(new Order(2, 2, 20));
        orderRepository.addOrder(new Order(3, 3, 30));
        orderRepository.addOrder(new Order(4, 4, 40));

    }*/
}
