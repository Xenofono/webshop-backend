package tech.kristoffer.webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.kristoffer.webshop.models.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
}
