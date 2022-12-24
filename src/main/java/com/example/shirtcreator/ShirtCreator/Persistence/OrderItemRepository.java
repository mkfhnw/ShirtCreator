package com.example.shirtcreator.ShirtCreator.Persistence;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    /*
    public List<OrderItem> findByOrderId();
    */
}
