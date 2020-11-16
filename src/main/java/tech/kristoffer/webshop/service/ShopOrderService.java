package tech.kristoffer.webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.kristoffer.webshop.models.Cart;
import tech.kristoffer.webshop.models.CartItem;
import tech.kristoffer.webshop.models.ShopOrder;
import tech.kristoffer.webshop.models.User;
import tech.kristoffer.webshop.repositories.ShopOrderRepository;
import tech.kristoffer.webshop.utilities.JsonMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ShopOrderService {

    @Autowired
    private JsonMapper jsonMapper;
    @Autowired
    private ShopOrderRepository shopOrderRepository;

    public List<CartItem> jsonToCartItem(Map<String, String> map){
        return jsonMapper.mapJsonToCartItem(map);
    }

    public void createOrder(User user){
        Cart cart = user.getCart();
        ShopOrder shopOrder = new ShopOrder();
        cart.getCartItems().forEach(item -> {
            String stringId = String.valueOf(item.getProduct().getId());
            String jsonItem = jsonMapper.mapCartItemToJson(item);
            System.out.println(jsonItem);
            shopOrder.getItems().put(stringId, jsonItem);
            shopOrder.setTotal(shopOrder.getTotal() + item.getSum());
        });
        shopOrder.setUser(user);
        shopOrderRepository.save(shopOrder);
    }

    public List<ShopOrder> findAll(){
        List<ShopOrder> shopOrders = new ArrayList<>();
        shopOrderRepository.findAll().forEach(shopOrders::add);
        return shopOrders;
    }

    public ShopOrder findById(String id) {
        long parsedId = Long.parseLong(id);
        return shopOrderRepository.findById(parsedId).orElseThrow(() -> new IllegalArgumentException("finns inte"));
    }

    public void expediteOrder(String id) {
        long parsedId = Long.parseLong(id);
        ShopOrder order = shopOrderRepository.findById(parsedId).orElseThrow(() -> new IllegalArgumentException("finns inte"));
        order.setExpedited(LocalDateTime.now());
        shopOrderRepository.save(order);
    }

    public List<ShopOrder> findByUserUsername(String name){
        return shopOrderRepository.findShopOrderByUserUsernameContaining(name);
    }


}
