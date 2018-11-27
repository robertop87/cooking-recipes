package edu.cooking.recipes.presentation;

import edu.cooking.recipes.application.recipes.RecipeEntry;
import edu.cooking.recipes.application.recipes.RecipeService;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class RecipeController {

  @Autowired
  private RecipeService recipeService;

  @PostMapping("/recipes")
  @ResponseBody
  public ResponseEntity<Void> registerRecipe(
      @RequestHeader("email-pwd") @NotBlank String emailPassword,
      @RequestBody @Valid RecipeEntry recipeEntry) throws UserNotFoundException {
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest().path("/{id}")
        .buildAndExpand(this.recipeService.registerRecipe(emailPassword, recipeEntry)).toUri();

    return ResponseEntity.created(location).build();
  }
}
