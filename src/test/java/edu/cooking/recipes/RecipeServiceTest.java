package edu.cooking.recipes;

import edu.cooking.recipes.application.recipes.RecipeEntry;
import edu.cooking.recipes.application.recipes.RecipeService;
import edu.cooking.recipes.application.recipes.RecipeToModify;
import edu.cooking.recipes.application.recipes.exceptions.RecipeNotFoundException;
import edu.cooking.recipes.application.users.UserEntry;
import edu.cooking.recipes.application.users.UserService;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import lombok.val;
import org.hamcrest.MatcherAssert;
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
    long recipeCreatedId = this.recipeService.register(ownerCredentials, recipe);

    Assert.assertThat(recipeCreatedId, Matchers.greaterThan(0L));
  }

  @Test(expected = UserNotFoundException.class)
  public void testRegisterNewRecipeNoOwner() throws UserNotFoundException {
    val recipe = RecipeEntry.builder()
        .name("Test recipe")
        .content("Cut the fruits, mix them and add sugar")
        .build();
    this.recipeService.register("noOne:WrongPassword", recipe);
  }

  @Test
  public void testGetAllRecipeForUserCredentials() throws UserNotFoundException {
    // 1. First step, a User must be registered first
    val owner = UserEntry.builder()
        .fullName("Owner of 2 recipes")
        .birthInDdMmYy("01-01-2019")
        .email("recipes.owner@email.com")
        .password("Password123")
        .build();

    val ownerCredentials = String.join(":", owner.getEmail(), owner.getPassword());
    this.userService.registerUser(owner);

    // 2. Second step create 2 Recipes, using Owner credentials email:password
    val recipe1 = RecipeEntry.builder()
        .name("Test recipe 1")
        .content("Cut the fruits, mix them and add sugar")
        .build();
    this.recipeService.register(ownerCredentials, recipe1);

    val recipe2 = RecipeEntry.builder()
        .name("Test recipe 2")
        .content("Cut the bread and cover with honey")
        .build();
    this.recipeService.register(ownerCredentials, recipe2);

    // 3. Get all recipes
    val results = this.recipeService.getAllByUserCredential(ownerCredentials);
    MatcherAssert.assertThat(results, Matchers.hasSize(2));
  }

  @Test
  public void testSearchRecipes() throws UserNotFoundException {
    // 1. First step, a User must be registered first
    val owner = UserEntry.builder()
        .fullName("Owner of searchable recipes")
        .birthInDdMmYy("01-01-2019")
        .email("recipes.for.search@email.com")
        .password("Password123")
        .build();

    val ownerCredentials = String.join(":", owner.getEmail(), owner.getPassword());
    this.userService.registerUser(owner);

    // 2. Second step create 2 Recipes, using Owner credentials email:password
    val recipe1 = RecipeEntry.builder()
        .name("Classic")
        .content("Cut the potato, mix them and add ice cream")
        .build();
    this.recipeService.register(ownerCredentials, recipe1);

    val recipe2 = RecipeEntry.builder()
        .name("Professional")
        .content("Cut the potato and cover with honey")
        .build();
    this.recipeService.register(ownerCredentials, recipe2);

    val recipe3 = RecipeEntry.builder()
        .name("Ultimate")
        .content("Cut the potato and cover with honey")
        .build();
    this.recipeService.register(ownerCredentials, recipe3);

    // 3. Search by keyword
    val results1 = this.recipeService.searchByWord("classic");
    MatcherAssert.assertThat(results1, Matchers.hasSize(1));

    val results2 = this.recipeService.searchByWord("professional");
    MatcherAssert.assertThat(results2, Matchers.hasSize(1));

    val results3 = this.recipeService.searchByWord("ultimate");
    MatcherAssert.assertThat(results3, Matchers.hasSize(1));

    val results4 = this.recipeService.searchByWord("potato");
    MatcherAssert.assertThat(results4, Matchers.hasSize(3));
  }

  @Test
  public void testUpdateRecipe() throws UserNotFoundException, RecipeNotFoundException {
    // 1. First step, a User must be registered first
    val owner = UserEntry.builder()
        .fullName("Owner of the updatable recipe")
        .birthInDdMmYy("01-01-2019")
        .email("recipe.update.owner@email.com")
        .password("Password123")
        .build();

    val ownerCredentials = String.join(":", owner.getEmail(), owner.getPassword());
    this.userService.registerUser(owner);

    // 2. Second step create a Recipe, using Owner credentials email:password
    val recipe = RecipeEntry.builder()
        .name("Test recipe normal")
        .content("Cut the fruits, mix them and add sugar")
        .build();
    this.recipeService.register(ownerCredentials, recipe);

    // 3. Update the created Recipe using a RecipeToModify
    val recipeUpdate = RecipeToModify.builder()
        .originalName(recipe.getName())
        .recipeEntry(new RecipeEntry("My updated name", "Just mix sugar and water"))
        .build();
    this.recipeService.update(ownerCredentials, recipeUpdate);

    // 4. Search by new name and compare values
    val updated = this.recipeService.searchByWord("My updated name");
    Assert.assertThat(updated, Matchers.hasSize(1));
  }

  @Test
  public void testDeleteRecipe() throws UserNotFoundException, RecipeNotFoundException {
    // 1. First step, a User must be registered first
    val owner = UserEntry.builder()
        .fullName("Owner of the future delete recipe")
        .birthInDdMmYy("01-01-2019")
        .email("recipe.delete.owner@email.com")
        .password("Password123")
        .build();

    val ownerCredentials = String.join(":", owner.getEmail(), owner.getPassword());
    this.userService.registerUser(owner);

    // 2. Second step create a Recipe, using Owner credentials email:password
    val recipe = RecipeEntry.builder()
        .name("To be deleted")
        .content("Cut the fruits, mix them and add sugar")
        .build();
    this.recipeService.register(ownerCredentials, recipe);

    // 3. Delete the latest created recipe
    this.recipeService.deleteByName(ownerCredentials, recipe.getName());

    // 4. Search by new name and compare values
    val updated = this.recipeService.searchByWord(recipe.getName());
    Assert.assertThat(updated, Matchers.empty());
  }
}
