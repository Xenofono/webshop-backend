package tech.kristoffer.webshop.service;

import org.springframework.stereotype.Service;
import tech.kristoffer.webshop.models.CartItem;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.models.User;
import tech.kristoffer.webshop.models.requests.AddToCartRequest;
import tech.kristoffer.webshop.repositories.ProductRepository;
import tech.kristoffer.webshop.repositories.UserRepository;
import tech.kristoffer.webshop.utilities.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class CartService {

    private JwtUtil jwtUtil;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public CartService(JwtUtil jwtUtil, ProductRepository productRepository, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void addToCart(AddToCartRequest addToCartRequest, HttpServletRequest request) {
        User user = jwtUtil.getUserFromRequest(request);
        Product productToAdd = productRepository.findById(addToCartRequest.getId())
                .orElseThrow(() -> new RuntimeException("Kunde inte hitta produkt"));

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(productToAdd);
        newCartItem.setQuantity(addToCartRequest.getQuantity());
        double sum = productToAdd.getPrice() * addToCartRequest.getQuantity();
        newCartItem.setSum(sum);


        Optional<CartItem> productAlreadyExistsInCart = user.getCart().getCartItems()
                .stream()
                .filter(item -> item.getProduct().equals(productToAdd))
                .findAny();

        productAlreadyExistsInCart.ifPresentOrElse(item -> {
                    user.removeCartItem(item);
                    user.addCartItem(newCartItem);

                }, () -> user.addCartItem(newCartItem));

        userRepository.save(user);

    }

    public void removeFromCart(String id, HttpServletRequest request) {
        long parsedId = Long.parseLong(id);

        User user = jwtUtil.getUserFromRequest(request);
        Product productToRemove = productRepository.findById(parsedId)
                .orElseThrow(() -> new RuntimeException("Kunde inte hitta produkt"));

        Optional<CartItem> cartItem = user.getCart().getCartItems()
                .stream()
                .filter(item -> item.getProduct().equals(productToRemove))
                .findAny();

        cartItem.ifPresent(user::removeCartItem);
        userRepository.save(user);


    }
}
