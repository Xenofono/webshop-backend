package tech.kristoffer.webshop.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.kristoffer.webshop.models.ShopOrder;

import java.util.List;

@Repository
public interface ShopOrderRepository extends CrudRepository<ShopOrder, Long> {
    List<ShopOrder> findShopOrderByUserUsernameContaining(String name);
}
