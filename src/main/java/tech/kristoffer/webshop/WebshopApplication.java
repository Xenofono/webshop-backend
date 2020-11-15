package tech.kristoffer.webshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.kristoffer.webshop.models.*;
import tech.kristoffer.webshop.repositories.*;
import tech.kristoffer.webshop.service.ShopOrderService;

import java.util.List;

@SpringBootApplication
public class WebshopApplication implements CommandLineRunner {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private ShopOrderService shopOrderService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



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
        product.setDescription("Fin och röd, du har den på kroppen, du vet vad en tröja är va?");
        product.setPrice(149.99);

        Product product2 = new Product();
        product2.setImageUrl("https://static.mio.host/images/products/35726.jpg?width=1920&height=1920&crop=false");
        product2.setName("Blå matta");
        product2.setPrice(195);
        product2.setDescription("Blå, den ska vara på golvet");
        productRepository.saveAll(List.of(product, product2));

        User newUser = new User();
        newUser.setUsername("kristoffer");
        newUser.setPassword(passwordEncoder.encode("pass"));

        User newUser2 = new User();
        newUser2.setUsername("alina");
        newUser2.setPassword(passwordEncoder.encode("pass"));

        userRepository.save(newUser);
        userRepository.save(newUser2);

        Authority authority = new Authority();
        authority.setUsername("kristoffer");
        authority.setAuthority("ROLE_ADMIN");

        Authority authority2 = new Authority();
        authority2.setUsername("alina");
        authority2.setAuthority("ROLE_USER");


        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(3);
        CartItem cartItem2 = new CartItem();
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(5);
        newUser2.addCartItem(cartItem);
        newUser2.addCartItem(cartItem2);
        shopOrderService.createOrder(newUser2);




        authorityRepository.save(authority);
        authorityRepository.save(authority2);
    }


}
