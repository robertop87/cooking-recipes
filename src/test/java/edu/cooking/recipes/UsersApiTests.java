package edu.cooking.recipes;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cooking.recipes.application.users.UserEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
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

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  public void testRegisterAnUserShouldReturnsCreatedStatus() throws Exception {
    UserEntry user = UserEntry.builder()
        .fullName("Test User")
        .birthInDdMmYy("17-09-2017")
        .email("test.user@email.com")
        .password("Password@123")
        .build();

    this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(user)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }
}
