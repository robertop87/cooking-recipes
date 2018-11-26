package edu.cooking.recipes;

import edu.cooking.recipes.domain.User;
import java.util.Date;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UsersApiTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testRegisterAnUserShouldReturnsCreatedStatus() throws Exception {
    val user = User.builder()
        .fullName("Test User")
        .birthDate(new Date())
        .email("test.user@email.com")
        .password("Password@123");

    this.mockMvc.perform(MockMvcRequestBuilders.post("/users", user))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }
}
