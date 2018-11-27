package edu.cooking.recipes;

import edu.cooking.recipes.application.recipes.RecipeEntry;
import edu.cooking.recipes.application.recipes.RecipeService;
import edu.cooking.recipes.application.users.UserEntry;
import edu.cooking.recipes.application.users.UserService;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import lombok.val;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class RecipeServiceTest {

  @Autowired
  private RecipeService recipeService;

  @Autowired
  private UserService userService;

  @Test
  public void testRegisterNewRecipe() throws UserNotFoundException {
    // 1. First step, a User must be registered first
    val owner = UserEntry.builder()
        .fullName("Owner of recipe")
        .birthInDdMmYy("01-01-2019")
        .email("recipe.owner@email.com")
        .password("Password123")
        .build();

    val ownerCredentials = String.join(":", owner.getEmail(), owner.getPassword());
    this.userService.registerUser(owner);

    // 2. Second step create a Recipe, using Owner credentials email:password
    val recipe = RecipeEntry.builder()
        .name("Test recipe")
        .content("Cut the fruits, mix them and add sugar")
        .build();
    long recipeCreatedId = this.recipeService.registerRecipe(ownerCredentials, recipe);

    Assert.assertThat(recipeCreatedId, Matchers.greaterThan(0L));
  }

  @Test(expected = UserNotFoundException.class)
  public void testRegisterNewRecipeNoOwner() throws UserNotFoundException {
    val recipe = RecipeEntry.builder()
        .name("Test recipe")
        .content("Cut the fruits, mix them and add sugar")
        .build();
    this.recipeService.registerRecipe("noOne:WrongPassword", recipe);
  }
}
