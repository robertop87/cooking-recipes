package edu.cooking.recipes.application.users;

import edu.cooking.recipes.commons.Dates;
import edu.cooking.recipes.domain.User;
import java.text.ParseException;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntry {

  @NotBlank
  private String fullName;

  @NotBlank
  private String password;

  @Email
  private String email;

  @NotBlank
  @Pattern(regexp = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)",
      message = "Invalid date format. The expected format is dd-mm-yyyy")
  private String birthInDdMmYy;

  public static User mapToDomain(UserEntry userEntry) throws ParseException {
    return User.builder()
        .fullName(userEntry.fullName)
        .email(userEntry.email)
        .password(userEntry.password)
        .birthDate(Dates.parseFrom(userEntry.birthInDdMmYy))
        .build();
  }

  public static void updateFrom(User user, UserEntry userEntry) throws ParseException {
    user.setEmail(userEntry.email);
    user.setFullName(userEntry.fullName);
    user.setBirthDate(Dates.parseFrom(userEntry.birthInDdMmYy));
    user.setPassword(userEntry.password);
  }
}
