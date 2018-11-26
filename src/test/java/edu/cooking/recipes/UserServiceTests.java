package edu.cooking.recipes;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import edu.cooking.recipes.application.users.UserEntry;
import edu.cooking.recipes.application.users.UserService;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import lombok.val;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserServiceTests {

  @Autowired
  private UserService userService;

  @Test
  public void testRegisterUser() {
    val user = UserEntry.builder()
        .fullName("Test User")
        .birthInDdMmYy("17-09-2017")
        .email("test.user@email.com")
        .password("Password@123")
        .build();

    val resultId = this.userService.registerUser(user);

    assertThat(resultId, Matchers.greaterThan(0L));
  }

  @Test
  public void testGetAllUsers() {
    val user1 = UserEntry.builder()
        .fullName("Tester One")
        .birthInDdMmYy("01-01-1111")
        .email("one.user@email.com")
        .password("Password@1")
        .build();

    val user2 = UserEntry.builder()
        .fullName("Tester Two")
        .birthInDdMmYy("02-02-2222")
        .email("two.user@email.com")
        .password("Password@1")
        .build();

    this.userService.registerUser(user1);
    this.userService.registerUser(user2);

    val registeredUsers = this.userService.getAllUsers();
    assertThat(registeredUsers, Matchers.hasSize(2));
  }

  @Test
  public void testGetExistingUserById() throws UserNotFoundException {
    val user = UserEntry.builder()
        .fullName("Personal user")
        .birthInDdMmYy("17-09-2017")
        .email("personal.user@email.com")
        .password("Password@123")
        .build();

    val resultId = this.userService.registerUser(user);

    val targetUser = this.userService.getById(resultId);

    assertThat(targetUser.getFullName(), equalTo(user.getFullName()));
    assertThat(targetUser.getEmail(), equalTo(user.getEmail()));
  }

  @Test(expected = UserNotFoundException.class)
  public void testSearchForNonExistingUserId() throws UserNotFoundException {
    this.userService.getById(Long.MAX_VALUE);
  }

  @Test
  public void testGetPersonalData() throws UserNotFoundException {
    val user = UserEntry.builder()
        .fullName("Personal user")
        .birthInDdMmYy("17-09-2017")
        .email("private.data@email.com")
        .password("private@data")
        .build();

    val resultId = this.userService.registerUser(user);

    val privateUser = this.userService.getPersonalData("private.data@email.com:private@data");

    assertThat(privateUser.getFullName(), equalTo(user.getFullName()));
    assertThat(privateUser.getEmail(), equalTo(user.getEmail()));
    assertThat(privateUser.getPassword(), equalTo(user.getPassword()));
  }

  @Test(expected = UserNotFoundException.class)
  public void testSearchPersonalDataWithWrongCredentials() throws UserNotFoundException {
    this.userService.getPersonalData("fake name:very wrong password");
  }
}
