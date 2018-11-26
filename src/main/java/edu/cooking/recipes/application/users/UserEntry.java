package edu.cooking.recipes.application.users;

import edu.cooking.recipes.commons.Dates;
import edu.cooking.recipes.domain.User;
import java.text.ParseException;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
  private String birthInDdMmYy;

  public static User mapToDomain(UserEntry userEntry) throws ParseException {
    if (Objects.isNull(userEntry.birthInDdMmYy)) {
      throw new ParseException("Cannot parse null Birth Dates", 0);
    }

    return User.builder()
        .fullName(userEntry.fullName)
        .email(userEntry.email)
        .password(userEntry.password)
        .birthDate(Dates.parseFrom(userEntry.birthInDdMmYy))
        .build();
  }
}
