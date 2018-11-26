package edu.cooking.recipes.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface Dates {

  SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

  static Date parseFrom(String ddmmyy) throws ParseException {
    return dateFormatter.parse(ddmmyy);
  }
}
