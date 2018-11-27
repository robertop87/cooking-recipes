package edu.cooking.recipes.persistence.recipes;

import edu.cooking.recipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
