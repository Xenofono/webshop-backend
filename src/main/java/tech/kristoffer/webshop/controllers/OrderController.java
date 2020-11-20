package tech.kristoffer.webshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.requests.AddToCartRequest;
import tech.kristoffer.webshop.models.responses.OrdersResponse;
import tech.kristoffer.webshop.models.responses.UserResponse;
import tech.kristoffer.webshop.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("shop/order")
public class OrderController {

    private UserService userService;

    public OrderController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<OrdersResponse> getOrders(HttpServletRequest request){
        OrdersResponse response = userService.getUserOrders(request);
        return ResponseEntity.ok(response);
    }


}
