package com.example.shirtcreator.ShirtCreator.Business;


import org.springframework.stereotype.Service;

@Service
public class CustomerVerification {

    public boolean validateEmailAddress(String email) {
        boolean valid = false;
        String[] addressParts = email.split("@"); // muss 2 nicht-leere Teile geben
        if (addressParts.length == 2 && !addressParts[0].isEmpty() && !addressParts[1].isEmpty()) {
            if (addressParts[1].charAt(addressParts[1].length() - 1) != '.') { // nach '.' muss was folgen
                String[] domainParts = addressParts[1].split("\\."); // muss min. 2 Teile mit min. 2 Zeichen geben
                if (domainParts.length >= 2) {
                    valid = true;
                    for (String s : domainParts) {
                        if (s.length() < 2) {
                            valid = false;
                            break;
                        }
                    }
                }
            }
        }
        return valid;
    }

}
