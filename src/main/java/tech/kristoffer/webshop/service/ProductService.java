package tech.kristoffer.webshop.service;

import org.springframework.stereotype.Service;
import tech.kristoffer.webshop.models.FormModel;
import tech.kristoffer.webshop.models.Product;
import tech.kristoffer.webshop.models.requests.FilterProductRequest;
import tech.kristoffer.webshop.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteById(long prodId) {
        productRepository.deleteById(prodId);
    }

    public void saveNewProduct(FormModel formModel) {

        double price = Double.parseDouble(formModel.getSecondInput());

        Product product = new Product();
        product.setName(formModel.getFirstInput());
        product.setPrice(price);
        product.setDescription(formModel.getThirdInput());
        product.setImageUrl(formModel.getFourthInput());
        productRepository.save(product);
    }

    public Product findById(String id) {
        long prodId = Long.parseLong(id);
        return productRepository.findById(prodId).orElseThrow(() -> new RuntimeException("id finns ej"));
    }

    public void updateProduct(FormModel formModel) {
        long prodId = Long.parseLong(formModel.getId());
        Product product = productRepository.findById(prodId).orElseThrow(RuntimeException::new);

        product.setName(formModel.getFirstInput());
        double newPrice = Double.parseDouble(formModel.getSecondInput());
        product.setPrice(newPrice);
        product.setDescription(formModel.getThirdInput());
        product.setImageUrl(formModel.getFourthInput());
        productRepository.save(product);

    }


    public Iterable<Product> findByNameContaining(FilterProductRequest request) {
        return productRepository.findByNameContaining(request.getName());
    }
}
