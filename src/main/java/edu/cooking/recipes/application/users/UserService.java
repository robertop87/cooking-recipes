package edu.cooking.recipes.application.users;

import java.util.Set;

public interface UserService {

  long registerUser(UserEntry userEntry);

  Set<UserGet> getAllUsers();
}
