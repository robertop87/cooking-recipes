package edu.cooking.recipes.application.recipes;

import edu.cooking.recipes.application.users.UserService;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import edu.cooking.recipes.domain.Recipe;
import edu.cooking.recipes.persistence.recipes.RecipeRepository;
import java.util.Date;
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
  public long registerRecipe(String emailPassword, RecipeEntry recipeEntry)
      throws UserNotFoundException {
    val recipeOwner = this.userService.getPersonalData(emailPassword);

    Recipe recipe = RecipeEntry.mapTo(recipeEntry);
    recipe.setUser(recipeOwner);
    recipe.setCreatedAt(new Date());

    return this.recipeRepository.save(recipe).getId();
  }
}
