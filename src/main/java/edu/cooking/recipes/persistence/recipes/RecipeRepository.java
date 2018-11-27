package edu.cooking.recipes.persistence.recipes;

import edu.cooking.recipes.domain.Recipe;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

  @Query(value = "SELECT r FROM Recipe r JOIN FETCH r.user u "
      + "WHERE u.email = :userEmail "
      + "AND u.password = :userPassword")
  Set<Recipe> findRecipesByUserCredential(@Param("userEmail") String userEmail,
      @Param("userPassword") String userPassword);
}
