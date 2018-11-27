package edu.cooking.recipes.application.recipes;

import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import java.util.Set;

public interface RecipeService {

  long registerRecipe(String emailPassword, RecipeEntry recipeEntry) throws UserNotFoundException;

  Set<RecipeEntry> getAllRecipes();

  Set<RecipeEntry> getAllByUserCredential(String emailPassword);

  Set<RecipeEntry> searchByWord(String searchWord);
}
