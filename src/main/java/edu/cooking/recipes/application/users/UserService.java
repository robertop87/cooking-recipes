package edu.cooking.recipes.application.users;

import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import java.util.Set;

public interface UserService {

  long registerUser(UserEntry userEntry);

  Set<UserGet> getAllUsers();

  UserGet getById(long userId) throws UserNotFoundException;
}
