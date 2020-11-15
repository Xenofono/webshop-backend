package tech.kristoffer.webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.kristoffer.webshop.models.ShopOrder;

@Repository
public interface ShopOrderRepository extends CrudRepository<ShopOrder, Long> {
}
