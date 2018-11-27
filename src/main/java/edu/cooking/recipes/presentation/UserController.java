package edu.cooking.recipes.presentation;

import edu.cooking.recipes.application.users.UserEntry;
import edu.cooking.recipes.application.users.UserGet;
import edu.cooking.recipes.application.users.UserService;
import edu.cooking.recipes.application.users.exceptions.BadDateFormatException;
import edu.cooking.recipes.application.users.exceptions.UserAlreadyRegisteredException;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import edu.cooking.recipes.domain.User;
import java.net.URI;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class UserController {

  @Autowired
  private UserService service;

  @PostMapping("/users")
  @ResponseBody
  public ResponseEntity<Void> registerUser(@RequestBody @Valid UserEntry userEntry)
      throws BadDateFormatException, UserAlreadyRegisteredException {
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

  @GetMapping("/users/personal")
  @ResponseBody
  public ResponseEntity<User> getPersonalUserData(
      @RequestHeader("email-pwd")
      @NotBlank String emailPassword)
      throws UserNotFoundException {
    return ResponseEntity.ok(this.service.getPersonalData(emailPassword));
  }

  @PutMapping("/users/personal")
  @ResponseBody
  public ResponseEntity<User> updatePersonalUserData(
      @RequestHeader("email-pwd")
      @NotBlank String emailPassword, @RequestBody @Valid UserEntry userEntry)
      throws UserNotFoundException, BadDateFormatException, UserAlreadyRegisteredException {
    this.service.updatePersonalData(emailPassword, userEntry);
    return ResponseEntity.ok().build();
  }
}
