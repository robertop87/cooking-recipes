package edu.cooking.recipes.presentation;

import edu.cooking.recipes.application.users.UserEntry;
import edu.cooking.recipes.application.users.UserGet;
import edu.cooking.recipes.application.users.UserService;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import java.net.URI;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class UserController {

  @Autowired
  private UserService service;

  @PostMapping("/users")
  @ResponseBody
  public ResponseEntity<Void> registerUser(@RequestBody @Valid UserEntry userEntry) {
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(this.service.registerUser(userEntry)).toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping("/users")
  @ResponseBody
  public Set<UserGet> getAllUsers() {
    return this.service.getAllUsers();
  }

  @GetMapping("/users/{id}")
  @ResponseBody
  public ResponseEntity<UserGet> getUserById(@PathVariable long id) throws UserNotFoundException {
    return ResponseEntity.ok(this.service.getById(id));
  }
}
