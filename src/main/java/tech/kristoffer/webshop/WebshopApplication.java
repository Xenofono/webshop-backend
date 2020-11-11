package tech.kristoffer.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import tech.kristoffer.webshop.entities.Product;
import tech.kristoffer.webshop.repositories.ProductRepository;

import java.util.List;

@SpringBootApplication
public class WebshopApplication implements CommandLineRunner {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(WebshopApplication.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("kristoffer.nasstrom@gmail.com");
//        message.setTo("kristoffer.nasstrom@gmail.com");
//        message.setSubject("Orderbekräftelse Kris webbshop");
//        message.setText("Du köpte en Ryzen 5900X 6100 kr");
//        javaMailSender.send(message);

        Product product = new Product();
        product.setImageUrl("https://imgprd19.hobbylobby.com/5/ba/61/5ba610f22c7cd6efb4e6c69387d938451a6c40e6/350Wx350H-633719-0320.jpg");
        product.setName("Röd tröja");
        product.setPrice(149.99);

        Product product2 = new Product();
        product2.setImageUrl("https://static.mio.host/images/products/35726.jpg?width=1920&height=1920&crop=false");
        product2.setName("Blå matta");
        product2.setPrice(195);
        productRepository.saveAll(List.of(product, product2));
    }
}
