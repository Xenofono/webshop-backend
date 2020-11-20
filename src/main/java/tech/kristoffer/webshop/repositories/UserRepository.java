package tech.kristoffer.webshop.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.kristoffer.webshop.models.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByUsername(String username);
    List<User> findUserByUsernameContaining(String username);
    List<User> findUserByAuthorityAuthority(String role);
}
