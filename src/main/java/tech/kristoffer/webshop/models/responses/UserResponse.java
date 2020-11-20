package tech.kristoffer.webshop.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.kristoffer.webshop.models.Cart;
import tech.kristoffer.webshop.models.ShopOrder;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Cart cart;
    private String username;

}
