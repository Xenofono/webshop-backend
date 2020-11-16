package tech.kristoffer.webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.kristoffer.webshop.models.Authority;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Integer> {
    Authority findByUsername(String username);
}
