package tech.kristoffer.webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.kristoffer.webshop.models.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
