package com.example.shirtcreator.ShirtCreator.Business;

import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Random;

@Service
public class AccountVerification {

    // Fields
    private final HashMap<String, Integer> tokenMap = new HashMap<>();

    // Default constructor

    // Methods
    public String generateLoginToken() {

        // Generate new random string based on random ints & make sure it's not used yet
        String token;
        Random random = new Random();
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        do {
            token = Hashing.sha256().hashString(random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString(), StandardCharsets.UTF_8).toString();
        } while (this.tokenMap.containsKey(token));

        return token;
    }

    public boolean validatePassword (String password){
        return password.length() >= 8;
    }

    // Wrapper methods
    public void put(String token, Integer id) {
        this.tokenMap.put(token, id);
    }

    public void remove(String token) {
        this.tokenMap.remove(token);
    }

    public boolean containsKey(String token) {
        return this.tokenMap.containsKey(token);
    }

}
