package tech.kristoffer.webshop.entities;

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
    private List<CartItem> cartItems;
    @OneToOne
    private User user;
    private double total;
}
