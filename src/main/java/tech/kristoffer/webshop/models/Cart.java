package tech.kristoffer.webshop.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    @JoinColumn(name = "cart_id")
    private List<CartItem> cartItems;
    @OneToOne
    private User user;
    private double total;
}
