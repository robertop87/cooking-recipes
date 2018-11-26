package edu.cooking.recipes;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import edu.cooking.recipes.application.users.UserEntry;
import edu.cooking.recipes.application.users.UserService;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import java.text.ParseException;
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

  @Test
  public void testUpdateExistingUser() throws UserNotFoundException, ParseException {
    val user = UserEntry.builder()
        .fullName("To be updated")
        .birthInDdMmYy("17-09-2017")
        .email("will.be.updated@email.com")
        .password("updatable")
        .build();

    // 1. Step register the user
    val createdId = this.userService.registerUser(user);

    // 2. Step update user data using credentials
    val newUserData = UserEntry.builder()
        .fullName("I'm updated")
        .birthInDdMmYy("5-5-2005")
        .email("was.updated@email.com")
        .password("strongPassword")
        .build();

    this.userService.updatePersonalData("will.be.updated@email.com:updatable", newUserData);

    // 3. Based on the original Id get the User and compare new values
    val updatedUser = this.userService.getById(createdId);

    assertThat(updatedUser.getEmail(), equalTo(newUserData.getEmail()));
    assertThat(updatedUser.getFullName(), equalTo(newUserData.getFullName()));
  }
}
