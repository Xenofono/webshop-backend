package tech.kristoffer.webshop.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.kristoffer.webshop.repositories.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("/admin")
public class AdminController {

    Logger logger = LoggerFactory.getLogger(AdminController.class);

    private ProductRepository productRepository;

    public AdminController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("products")
    public String getAdminPage(Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("username", username);
        return "admin";
    }

    @GetMapping("/orders")
    public String getOrderPage(Model model){
        return "orders";
    }

    @GetMapping("removeproduct/{id}")
    public String removeProduct(@PathVariable String id){
        logger.debug("Fick id: " + id);
        logger.trace("Fick id: " + id);
        long prodId = Long.parseLong(id);
        productRepository.deleteById(prodId);
        return "redirect:/admin/products";
    }

    private <T> List<T> iterableToList(Iterable<T> iterable){
        return StreamSupport.stream(iterable.spliterator(), true)
                .collect(Collectors.toList());
    }
}
