package tech.kristoffer.webshop.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.criterion.Order;
import tech.kristoffer.webshop.models.ShopOrder;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {

    private Set<ShopOrder> orders;
}
