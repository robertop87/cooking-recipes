package edu.cooking.recipes.application.recipes;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeToModify {

  @NotBlank
  private String originalName;

  @Valid
  private RecipeEntry recipeEntry;
}
