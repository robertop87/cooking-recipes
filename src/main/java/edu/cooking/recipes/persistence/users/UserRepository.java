package edu.cooking.recipes.persistence.users;

import edu.cooking.recipes.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
