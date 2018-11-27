package edu.cooking.recipes.presentation;

import edu.cooking.recipes.application.recipes.RecipeEntry;
import edu.cooking.recipes.application.recipes.RecipeService;
import edu.cooking.recipes.application.recipes.RecipeToModify;
import edu.cooking.recipes.application.recipes.exceptions.RecipeNotFoundException;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import java.net.URI;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
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
        .buildAndExpand(this.recipeService.register(emailPassword, recipeEntry)).toUri();

    return ResponseEntity.created(location).build();
  }

  @GetMapping("/recipes")
  @ResponseBody
  public Page<RecipeEntry> getAllRecipes(Pageable pageable) {
    return this.recipeService.getAllByPage(pageable);
  }

  @GetMapping("/recipes/personal")
  @ResponseBody
  public Set<RecipeEntry> getAllRecipes(
      @RequestHeader("email-pwd") @NotBlank String emailPassword) {
    return this.recipeService.getAllByUserCredential(emailPassword);
  }

  @GetMapping("/recipes/search")
  @ResponseBody
  public Set<RecipeEntry> searchRecipes(@RequestParam("keyWord") @NotBlank String keyWord) {
    return this.recipeService.searchByWord(keyWord.trim());
  }

  @PutMapping("/recipes/personal")
  @ResponseBody
  public void updateRecipe(
      @RequestHeader("email-pwd") @NotBlank String emailPassword,
      @RequestBody @Valid RecipeToModify recipeToModify)
      throws UserNotFoundException, RecipeNotFoundException {
    this.recipeService.update(emailPassword, recipeToModify);
  }

  @DeleteMapping("/recipes/personal")
  @ResponseBody
  public void deleteRecipe(
      @RequestHeader("email-pwd") @NotBlank String emailPassword,
      @RequestParam("recipeNameToDelete") @NotBlank String recipeNameToDelete)
      throws UserNotFoundException, RecipeNotFoundException {
    this.recipeService.deleteByName(emailPassword, recipeNameToDelete.trim());
  }
}
