package tech.kristoffer.webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.models.requests.FilterProductRequest;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    Iterable<Product> findByNameContaining(String name);
}
