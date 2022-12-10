package ShirtCreator.Business;

import ShirtCreator.Persistence.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderVerification {

    public boolean validateOrder(Order o) {
        return true;
    }
}
