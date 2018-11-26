package edu.cooking.recipes.application.users;

import edu.cooking.recipes.domain.User;
import edu.cooking.recipes.persistence.users.UserRepository;
import java.text.ParseException;
import java.util.Date;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository repository;

  @Autowired
  public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
  }

  @Override
  public long registerUser(UserEntry userEntry) {
    try {
      User userToRegister = UserEntry.mapToDomain(userEntry);
      userToRegister.setRegisteredAt(new Date());
      return this.repository.save(userToRegister).getId();
    } catch (ParseException e) {
      // TODO move this exception as Web Response
      e.printStackTrace();
      log.warning("Cannot parse the UserEntry");
    }
    return 0;
  }
}
