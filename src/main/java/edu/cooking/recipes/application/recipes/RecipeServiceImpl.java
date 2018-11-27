package edu.cooking.recipes.application.recipes;

import edu.cooking.recipes.application.recipes.exceptions.RecipeNotFoundException;
import edu.cooking.recipes.application.users.UserService;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import edu.cooking.recipes.commons.Credentials;
import edu.cooking.recipes.domain.Recipe;
import edu.cooking.recipes.persistence.recipes.RecipeRepository;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

  private RecipeRepository recipeRepository;
  private UserService userService;

  @Autowired
  public RecipeServiceImpl(RecipeRepository recipeRepository, UserService userService) {
    this.recipeRepository = recipeRepository;
    this.userService = userService;
  }

  @Override
  public long register(String emailPassword, RecipeEntry recipeEntry) throws UserNotFoundException {
    val recipeOwner = this.userService.getPersonalData(emailPassword);

    Recipe recipe = RecipeEntry.mapTo(recipeEntry);
    recipe.setUser(recipeOwner);
    recipe.setCreatedAt(new Date());

    return this.recipeRepository.save(recipe).getId();
  }

  @Override
  public Set<RecipeEntry> getAll() {
    return this.mapToSet(this.recipeRepository.findAll());
  }

  @Override
  public Set<RecipeEntry> getAllByUserCredential(String emailPassword) {
    val credentials = Credentials.getCredentials(emailPassword);
    return this.mapToSet(
        this.recipeRepository.findRecipesByUserCredential(
            credentials.get("email"), credentials.get("password")));
  }

  @Override
  public Set<RecipeEntry> searchByWord(String searchWord) {
    return this.mapToSet(this.recipeRepository.searchWordInRecipe(searchWord.toUpperCase()));
  }

  @Override
  public void update(String emailPassword, RecipeToModify recipeToModify)
      throws UserNotFoundException, RecipeNotFoundException {
    val owner = this.userService.getPersonalData(emailPassword);

    val results =
        this.recipeRepository.getRecipeByNameAndUserEmail(
            recipeToModify.getOriginalName(), owner.getEmail());

    Recipe recipeToUpdate = results.stream().findFirst().orElseThrow(RecipeNotFoundException::new);

    recipeToUpdate.setName(recipeToModify.getRecipeEntry().getName());
    recipeToUpdate.setContent(recipeToModify.getRecipeEntry().getContent());

    this.recipeRepository.save(recipeToUpdate);
  }

  @Override
  public void deleted(String emailPassword, RecipeToModify recipeToModify)
      throws UserNotFoundException {}

  private Set<RecipeEntry> mapToSet(Iterable<Recipe> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false)
        .map(RecipeEntry::mapFrom)
        .collect(Collectors.toSet());
  }
}
