package tech.kristoffer.webshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.requests.AddToCartRequest;
import tech.kristoffer.webshop.models.responses.OrdersResponse;
import tech.kristoffer.webshop.models.responses.UserResponse;
import tech.kristoffer.webshop.service.ShopOrderService;
import tech.kristoffer.webshop.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("shop/order")
public class OrderController {

    private UserService userService;
    private ShopOrderService orderService;

    public OrderController(UserService userService, ShopOrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<OrdersResponse> getOrders(HttpServletRequest request){
        OrdersResponse response = userService.getUserOrders(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> completeOrder(HttpServletRequest request){
        orderService.createOrder(request);
        return ResponseEntity.ok().build();
    }


}
