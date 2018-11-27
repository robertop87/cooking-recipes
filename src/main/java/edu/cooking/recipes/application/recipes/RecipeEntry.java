package edu.cooking.recipes.application.recipes;

import edu.cooking.recipes.domain.Recipe;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RecipeEntry {

  @NotBlank
  private String name;

  @NotBlank
  private String content;

  /**
   * Map a {@link RecipeEntry} to {@link Recipe}.
   * @param recipeEntry the entry from outside
   * @return a {@link Recipe}
   */
  public static Recipe mapTo(RecipeEntry recipeEntry) {
    return Recipe.builder()
        .name(recipeEntry.name)
        .content(recipeEntry.content)
        .build();
  }
}