package com.example.shirtcreator.ShirtCreator.Business;

import com.example.shirtcreator.ShirtCreator.Persistence.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderVerification {

    public static final int MAX_QUANTITY = 80; // Maximale Anzahl bestellbarer T-Shirts

    public boolean validateOrder(Order o) {
        if (o.getQuantity() > MAX_QUANTITY) {
            return false;
        }
        return true;
    }


}
