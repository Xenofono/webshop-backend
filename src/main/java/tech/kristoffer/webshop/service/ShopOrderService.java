package tech.kristoffer.webshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tech.kristoffer.webshop.models.Cart;
import tech.kristoffer.webshop.models.CartItem;
import tech.kristoffer.webshop.models.ShopOrder;
import tech.kristoffer.webshop.models.User;
import tech.kristoffer.webshop.repositories.ShopOrderRepository;
import tech.kristoffer.webshop.utilities.JsonMapper;
import tech.kristoffer.webshop.utilities.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ShopOrderService {

    private JsonMapper jsonMapper;
    private ShopOrderRepository shopOrderRepository;
    private JwtUtil jwtUtil;
    private JavaMailSender javaMailSender;

    public ShopOrderService(JsonMapper jsonMapper, ShopOrderRepository shopOrderRepository, JwtUtil jwtUtil, JavaMailSender javaMailSender) {
        this.jsonMapper = jsonMapper;
        this.shopOrderRepository = shopOrderRepository;
        this.jwtUtil = jwtUtil;
        this.javaMailSender = javaMailSender;
    }

    public List<CartItem> jsonToCartItem(Map<String, String> map) {
        return jsonMapper.mapJsonToCartItem(map);
    }

    public void createOrder(User user) {
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

    public void createOrder(HttpServletRequest request) {
        User user = jwtUtil.getUserFromRequest(request);

        ShopOrder shopOrder = new ShopOrder();
        user.getCart().getCartItems().forEach(item -> {
            String stringId = String.valueOf(item.getProduct().getId());
            String jsonItem = jsonMapper.mapCartItemToJson(item);
            System.out.println(jsonItem);
            shopOrder.getItems().put(stringId, jsonItem);
            shopOrder.setTotal(shopOrder.getTotal() + item.getSum());
        });
        sendConfirmationEmail(user);
        user.getCart().clearCart();
        shopOrder.setUser(user);
        shopOrderRepository.save(shopOrder);
    }

    public List<ShopOrder> findAll() {
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

    public List<ShopOrder> findByUserUsername(String name) {
        return shopOrderRepository.findShopOrderByUserUsernameContaining(name);
    }

    private void sendConfirmationEmail(User user) {
        System.out.println(user.getCart().getTotal());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kristoffer.nasstrom@gmail.com");
        message.setTo("kristoffer.nasstrom@gmail.com");
        message.setSubject("Orderbekräftelse Kris webbshop");
        String items = user.getCart().getCartItems().stream().map(item -> {
            String itemString =
                    "ID: " + item.getId() + " NAMN: " + item.getProduct().getName()
                            + " KVANTITET: " + item.getQuantity() + " PRIS: " + item.getSum() + " kr";
            return itemString;
        }).collect(Collectors.joining("\n************\n"));
        String greetingAndTotal = "Tack för din beställning " + user.getUsername() + ", din beställning blev totalt "
                + user.getCart().getTotal() + " kr";
        message.setText(greetingAndTotal + "\n\n" + items);

        CompletableFuture.runAsync(() -> javaMailSender.send(message));

    }


}
