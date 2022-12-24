package com.example.shirtcreator.ShirtCreator.Services.Order;

import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderItemService {


    /*
    @Autowired
    private OrderItemRepository orderItemRepository;


@GetMapping(path = "/api/orderItem", produces = "application/json")
private List<MessageOrderItem> getOrderItems(@RequestParam int orderId) {

    List<OrderItem> orderItems = orderItemRepository.findByOrderId();
    List<MessageOrderItem> m = new ArrayList<>();
    for (OrderItem o : orderItems) {
        MessageOrderItem moi = new MessageOrderItem();
        moi.setConfigurationId(orderId);
        moi.setQuantity(o.getQuantity());
        m.add(moi);
    }
    return m;


}
 */

}
