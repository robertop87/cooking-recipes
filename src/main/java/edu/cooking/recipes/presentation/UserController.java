package edu.cooking.recipes.presentation;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

  @PostMapping("/users")
  public ResponseEntity<Void> registeUser() {
    return ResponseEntity.created(URI.create("test")).build();
  }
}
