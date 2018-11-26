package edu.cooking.recipes.application.users;

import edu.cooking.recipes.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGet {

  private String fullName;
  private String email;

  public static UserGet mapFrom(User user) {
    return new UserGet(user.getFullName(), user.getEmail());
  }
}
