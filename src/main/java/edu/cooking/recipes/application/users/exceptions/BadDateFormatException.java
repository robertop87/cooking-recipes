package edu.cooking.recipes.application.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Cannot parse the provide Date, ensure the next format dd-MM-yyyy")
public class BadDateFormatException extends Exception {
}
