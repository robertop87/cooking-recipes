package edu.cooking.recipes.persistence.users;

import edu.cooking.recipes.domain.User;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  Optional<User> findByEmailAndPassword(String email, String password);

  Optional<User> findByEmail(String email);
}
