package tech.kristoffer.webshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.requests.AddToCartRequest;
import tech.kristoffer.webshop.models.responses.UserResponse;
import tech.kristoffer.webshop.service.CartService;
import tech.kristoffer.webshop.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("shop/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody AddToCartRequest addToCartRequest, HttpServletRequest request){
        cartService.addToCart(addToCartRequest, request);
        return ResponseEntity.ok("Produkt tillagd");

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeFromCart(@PathVariable("id") String id, HttpServletRequest request){
        cartService.removeFromCart(id, request);
        return ResponseEntity.ok().build();
    }
}
