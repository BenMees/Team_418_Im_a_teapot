package com.team418;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Utility {

    public static String generateBase64Authorization(String email, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((email+":"+password).getBytes(StandardCharsets.UTF_8));
    }

}
