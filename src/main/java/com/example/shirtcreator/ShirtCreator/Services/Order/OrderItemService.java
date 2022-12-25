package com.example.shirtcreator.ShirtCreator.Services.Order;

import com.example.shirtcreator.ShirtCreator.Persistence.OrderItem;
import com.example.shirtcreator.ShirtCreator.Persistence.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;


//    @GetMapping(path = "/api/orderItem", produces = "application/json")
//    private List<MessageOrderItem> getOrderItems(@RequestParam int orderId) {
//
//        List<OrderItem> orderItems = orderItemRepository.findByOrderId();
//        List<MessageOrderItem> m = new ArrayList<>();
//        for (OrderItem o : orderItems) {
//            MessageOrderItem moi = new MessageOrderItem();
//            moi.setConfigurationId(orderId);
//            moi.setQuantity(o.getQuantity());
//            m.add(moi);
//        }
//        return m;
//
//
//    }

}
