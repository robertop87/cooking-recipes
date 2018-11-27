package edu.cooking.recipes.application.recipes;

import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;

public interface RecipeService {

  long registerRecipe(String emailPassword, RecipeEntry recipeEntry) throws UserNotFoundException;
}
