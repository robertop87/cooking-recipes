package edu.cooking.recipes.application.users;

import java.util.Optional;
import java.util.Set;

public interface UserService {

  long registerUser(UserEntry userEntry);

  Set<UserGet> getAllUsers();

  Optional<UserGet> getById(long userId);
}
