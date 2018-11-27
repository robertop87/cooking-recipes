package edu.cooking.recipes.application.users;

import edu.cooking.recipes.application.users.exceptions.BadDateFormatException;
import edu.cooking.recipes.application.users.exceptions.UserAlreadyRegisteredException;
import edu.cooking.recipes.application.users.exceptions.UserNotFoundException;
import edu.cooking.recipes.commons.Credentials;
import edu.cooking.recipes.domain.User;
import edu.cooking.recipes.persistence.users.UserRepository;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.extern.java.Log;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public long registerUser(UserEntry userEntry)
      throws BadDateFormatException, UserAlreadyRegisteredException {
    if (this.repository.findByEmail(userEntry.getEmail().trim()).isPresent()) {
      throw new UserAlreadyRegisteredException();
    }
    try {
      User userToRegister = UserEntry.mapToDomain(userEntry);
      userToRegister.setRegisteredAt(new Date());
      return this.repository.save(userToRegister).getId();
    } catch (ParseException e) {
      log.info("Cannot parse the given date");
      throw new BadDateFormatException();
    }
  }

  @Override
  public Set<UserGet> getAllUsers() {
    return StreamSupport
        .stream(this.repository.findAll().spliterator(), false)
        .map(UserGet::mapFrom)
        .collect(Collectors.toSet());
  }

  @Override
  public Page<UserGet> getAllUsersByPage(Pageable pageable) {
    Page<User> userPage = this.repository.findAll(pageable);
    return userPage.map(UserGet::mapFrom);
  }

  @Override
  public UserGet getById(long userId) throws UserNotFoundException {
    return this.repository.findById(userId)
        .map(UserGet::mapFrom).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public User getPersonalData(String emailPassword) throws UserNotFoundException {
    val credentials = Credentials.getCredentials(emailPassword);
    return this.repository
        .findByEmailAndPassword(credentials.get("email"), credentials.get("password"))
        .orElseThrow(UserNotFoundException::new);
  }

  @Override
  public void updatePersonalData(String currentEmailPassword, UserEntry userEntry)
      throws UserNotFoundException, BadDateFormatException, UserAlreadyRegisteredException {
    val currentEmail = Credentials.getCredentials(currentEmailPassword).get("email");
    userEntry.setEmail(userEntry.getEmail().trim());

    if (!currentEmail.equalsIgnoreCase(userEntry.getEmail())) {
      if (this.repository.findByEmail(userEntry.getEmail()).isPresent()) {
        throw new UserAlreadyRegisteredException();
      }
    }
    try {
      User userToBeUpdated = this.getPersonalData(currentEmailPassword);
      UserEntry.updateFrom(userToBeUpdated, userEntry);
      this.repository.save(userToBeUpdated);
    } catch (ParseException e) {
      log.info("Cannot parse the given date");
      throw new BadDateFormatException();
    }
  }
}
