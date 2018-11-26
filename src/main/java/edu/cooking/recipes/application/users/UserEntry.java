package edu.cooking.recipes.application.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntry {

  @NotBlank
  private String fullName;

  @NotBlank
  private String password;

  @Email
  private String email;

  @NotBlank
  private String birthInDdMmYy;
}
