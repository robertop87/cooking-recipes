package edu.cooking.recipes.application.recipes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cannot found a match values recipe")
public class RecipeNotFoundException extends Exception {
}
