package edu.cooking.recipes.application.users;

import edu.cooking.recipes.application.users.exceptions.BadDateFormatException;
import edu.cooking.recipes.application.users.exceptions.UserAlreadyRegisteredException;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import edu.cooking.recipes.domain.User;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  long registerUser(UserEntry userEntry)
      throws BadDateFormatException, UserAlreadyRegisteredException;

  Set<UserGet> getAllUsers();

  Page<UserGet> getAllUsersByPage(Pageable pageable);

  UserGet getById(long userId) throws UserNotFoundException;

  User getPersonalData(String emailPassword) throws UserNotFoundException;

  void updatePersonalData(String currentEmailPassword, UserEntry userEntry)
      throws UserNotFoundException, BadDateFormatException, UserAlreadyRegisteredException;
}
