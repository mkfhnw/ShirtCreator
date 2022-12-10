package Business;

import Models.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderVerification {

    public boolean validateOrder(Order o) {
        return true;
    }
}
