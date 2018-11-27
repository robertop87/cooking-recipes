package edu.cooking.recipes.application.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The user email is already registered")
public class UserAlreadyRegisteredException extends Exception {
}
