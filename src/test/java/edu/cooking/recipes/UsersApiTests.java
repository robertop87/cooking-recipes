package edu.cooking.recipes;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.cooking.recipes.application.users.UserEntry;
import lombok.val;
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
    val user = UserEntry.builder()
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

  @Test
  public void testRegisterInvalidEmailDataShouldReturnBadRequest() throws Exception {
    val user = UserEntry.builder()
        .fullName("Test User")
        .birthInDdMmYy("17-09-2017")
        .email("it is not a email")
        .password("Password@123")
        .build();

    this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(user)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void testRegisterEmptyNameShouldReturnBadRequest() throws Exception {
    val user = UserEntry.builder()
        .fullName("")
        .birthInDdMmYy("17-09-2017")
        .email("mail@mail.com")
        .password("Password@123")
        .build();

    this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(user)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void testRegisterInvalidDateFormatShouldReturnBadRequest() throws Exception {
    val user = UserEntry.builder()
        .fullName("Test User")
        .birthInDdMmYy("17/09/2017")
        .email("an@email.com")
        .password("Password@123")
        .build();

    this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(user)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void testRegisterEmptyPasswordShouldReturnBadRequest() throws Exception {
    val user = UserEntry.builder()
        .fullName("Test User")
        .birthInDdMmYy("17-09-2017")
        .email("an@email.com")
        .password("")
        .build();

    this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(user)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  public void testGetByUserShouldReturnsOk() throws Exception {
    val user = UserEntry.builder()
        .fullName("Test User")
        .birthInDdMmYy("17-09-2017")
        .email("test.user@email.com")
        .password("Password@123")
        .build();

    val createUserLocation = this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(user)))
        .andReturn()
        .getResponse()
        .getHeader("Location");

    this.mockMvc.perform(MockMvcRequestBuilders.get(createUserLocation))
      .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testGetByUserNonExistingIdShouldReturnsNotFound() throws Exception {
    val url = "http://localhost/users"+Long.MAX_VALUE;

    this.mockMvc.perform(MockMvcRequestBuilders.get(url))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testGetPersonalDataWithCredentialsReturnsOk() throws Exception {
    val user = UserEntry.builder()
        .fullName("Test User")
        .birthInDdMmYy("17-09-2017")
        .email("private.user@email.com")
        .password("privatepassword")
        .build();

    val createUserLocation = this.mockMvc.perform(MockMvcRequestBuilders.post("/users")
        .contentType(MediaType.APPLICATION_JSON)
        .content(this.objectMapper.writeValueAsString(user)))
        .andReturn()
        .getResponse()
        .getHeader("Location");

    this.mockMvc.perform(MockMvcRequestBuilders.get("/users/personal")
        .header("email-pwd", "private.user@email.com:privatepassword"))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void testGetPersonalDataWithWrongCredentialsReturnsNotFound() throws Exception {
    this.mockMvc.perform(MockMvcRequestBuilders.get("/users/personal")
        .header("email-pwd", "badEmail:badPassword"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
