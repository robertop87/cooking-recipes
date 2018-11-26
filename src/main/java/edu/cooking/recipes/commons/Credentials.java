package edu.cooking.recipes.commons;

import java.util.HashMap;
import java.util.Map;
import lombok.val;

public interface Credentials {

  static Map<String, String> getCredentials(String emailPassword) {
    val splitData = emailPassword.split(":");
    Map<String, String> credentials = new HashMap<>();
    credentials.put("email", splitData[0]);
    credentials.put("password", splitData[1]);
    return credentials;
  }
}
