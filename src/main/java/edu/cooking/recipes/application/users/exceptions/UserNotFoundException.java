package edu.cooking.recipes.application.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The user is not found")
public class UserNotFoundException extends Exception {
}
