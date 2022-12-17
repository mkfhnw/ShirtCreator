package com.example.shirtcreator.ShirtCreator.Business;

import com.example.shirtcreator.ShirtCreator.Persistence.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderVerification {

    public boolean validateOrder(Order o) {
        return true;
    }
}
