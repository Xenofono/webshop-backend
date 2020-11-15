package tech.kristoffer.webshop.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.kristoffer.webshop.models.Cart;
import tech.kristoffer.webshop.models.CartItem;
import tech.kristoffer.webshop.models.JsonCartItem;
import tech.kristoffer.webshop.models.ShopOrder;
import tech.kristoffer.webshop.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JsonMapper {

    private  final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProductRepository productRepository;

    public JsonMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public  String mapCartItemToJson(CartItem cartItem) {
        String json = null;
        try {
            JsonCartItem jsonCartItem = new JsonCartItem(cartItem);
            json = objectMapper.writeValueAsString(jsonCartItem);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public  List<CartItem> mapJsonToCartItem(Map<String, String> map){
        List<CartItem> cartItems = new ArrayList<>();
        map.forEach((key, value) -> {
            try {
                JsonCartItem jsonCartItem = objectMapper.readValue(value, JsonCartItem.class);
                CartItem cartItem = new CartItem();
                cartItem.setQuantity(jsonCartItem.getQuantity());
                cartItem.setProduct(productRepository.findById(Long.parseLong(key)).get());
                cartItem.setSum(jsonCartItem.getSum());
                cartItems.add(cartItem);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        });
        return cartItems;
    }
}
