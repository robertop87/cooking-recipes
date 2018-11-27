package edu.cooking.recipes.application.recipes;

import edu.cooking.recipes.application.recipes.exceptions.RecipeNotFoundException;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecipeService {

  long register(String emailPassword, RecipeEntry recipeEntry) throws UserNotFoundException;

  Set<RecipeEntry> getAll();

  Page<RecipeEntry> getAllByPage(Pageable pageable);

  Set<RecipeEntry> getAllByUserCredential(String emailPassword);

  Set<RecipeEntry> searchByWord(String searchWord);

  void update(String emailPassword, RecipeToModify recipeToModify)
      throws UserNotFoundException, RecipeNotFoundException;

  void deleteByName(String emailPassword, String recipeName)
      throws UserNotFoundException, RecipeNotFoundException;
}
