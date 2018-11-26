package edu.cooking.recipes.presentation;

import edu.cooking.recipes.application.users.UserEntry;
import edu.cooking.recipes.application.users.UserGet;
import edu.cooking.recipes.application.users.UserService;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class UserController {

  @Autowired
  private UserService service;

  @PostMapping("/users")
  @ResponseBody
  public ResponseEntity<Void> registerUser(UriComponentsBuilder uriComponentsBuilder,
      @RequestBody @Valid UserEntry userEntry) {

    UriComponents uriComponents = uriComponentsBuilder.path("/users/{id}")
        .buildAndExpand(this.service.registerUser(userEntry));

    return ResponseEntity.created(uriComponents.toUri()).build();
  }

  @GetMapping
  @ResponseBody
  public Set<UserGet> getAllUsers() {
    return this.service.getAllUsers();
  }
}
