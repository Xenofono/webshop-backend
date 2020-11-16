package tech.kristoffer.webshop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.kristoffer.webshop.models.*;
import tech.kristoffer.webshop.repositories.ProductRepository;
import tech.kristoffer.webshop.repositories.ShopOrderRepository;
import tech.kristoffer.webshop.service.ProductService;
import tech.kristoffer.webshop.service.ShopOrderService;
import tech.kristoffer.webshop.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    private ProductService productService;
    private ShopOrderService shopOrderService;
    private UserService userService;

    public AdminController(ProductService productService, ShopOrderService shopOrderService, UserService userService) {
        this.productService = productService;
        this.shopOrderService = shopOrderService;
        this.userService = userService;
    }

    @GetMapping("products")
    public String getProductPage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("products", productService.findAll());
        model.addAttribute("username", username);
        return "admin";
    }

    @GetMapping("/orders")
    public String getOrderPage(@RequestParam(value = "username", required = false) String username, Model model) {
        model.addAttribute("form", new FormModel());

        if (username == null || username.isBlank()) {
            model.addAttribute("orders", shopOrderService.findAll());
        } else {
            List<ShopOrder> orders = shopOrderService.findByUserUsername(username);
            model.addAttribute("orders", orders);
        }
        return "orders";
    }

    @PostMapping("/orders")
    public String getOrderPageSubmission(@ModelAttribute FormModel formModel) {
        return "redirect:/admin/orders?username=" + formModel.getFirstInput();
    }

    @GetMapping("/orders/{id}")
    public String getSpecifikOrder(@PathVariable String id, Model model) {
        ShopOrder order = shopOrderService.findById(id);
        List<CartItem> items = shopOrderService.jsonToCartItem(order.getItems());
        model.addAttribute("order", order);
        model.addAttribute("items", items);
        return "order";
    }

    @GetMapping("removeproduct/{id}")
    public String removeProduct(@PathVariable String id) {
        logger.debug("Fick id: " + id);
        logger.trace("Fick id: " + id);
        long prodId = Long.parseLong(id);
        productService.deleteById(prodId);
        return "redirect:/admin/products";
    }

    @PostMapping("expediteproduct/{id}")
    public String expediteProduct(@PathVariable String id) {
        logger.debug("Fick id: " + id);
        logger.trace("Fick id: " + id);
        shopOrderService.expediteOrder(id);
        return "redirect:/admin/orders/" + id;
    }

    @GetMapping("/users")
    public String getUsersPage(@RequestParam(value = "username", required = false) String username, Model model) {
        model.addAttribute("form", new FormModel());

        if (username == null || username.isBlank()) {
            model.addAttribute("users", userService.findAll());
        } else {
            List<User> users = userService.findUserByUsernameContaining(username);
            model.addAttribute("users", users);
        }
        return "users";
    }

    @PostMapping("/users")
    public String getUserPageSubmission(@ModelAttribute FormModel formModel) {
        return "redirect:/admin/users?username=" + formModel.getFirstInput();
    }

    @GetMapping("/add-admin")
    public String getAdminPage(Model model) {
        model.addAttribute("form", new FormModel());

        List<User> admins = userService.findAllAdmins();
        model.addAttribute("admins", admins);
        return "add-admin";
    }

    @PostMapping("/add-admin")
    public String postNewAdmin(@ModelAttribute FormModel formModel) {
        userService.saveNewAdmin(formModel);

        return "redirect:/admin/add-admin";
    }

    @GetMapping("/add-product")
    public String getAddProductPage(@RequestParam(value="id",required = false) String id, Model model) {
        FormModel form = new FormModel();

        if (id != null) {
            Product product = productService.findById(id);
            model.addAttribute("edit", true);
            form.setId(id);
            form.setFirstInput(product.getName());
            form.setSecondInput(String.valueOf(product.getPrice()));
            form.setThirdInput(product.getDescription());
            form.setFourthInput(product.getImageUrl());

        }
        model.addAttribute("form", form);

        return "add-product";
    }

    @PostMapping("/add-product")
    public String postNewProduct(@ModelAttribute FormModel formModel) {
        productService.saveNewProduct(formModel);
        return "redirect:/admin/products";
    }

    @PutMapping("/add-product")
    public String putEditProduct(@ModelAttribute FormModel formModel){
        System.out.println("HEJ");
        productService.updateProduct(formModel);
        return "redirect:/admin/products";
    }

    private <T> List<T> iterableToList(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), true)
                .collect(Collectors.toList());
    }
}
