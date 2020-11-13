package tech.kristoffer.webshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.repositories.ProductRepository;

@RestController
@RequestMapping("shop")
public class ShopController {

    private ProductRepository productRepository;

    public ShopController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public Iterable<Product> getAllProducts(){
        return productRepository.findAll();
    }
}
