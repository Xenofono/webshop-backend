package tech.kristoffer.webshop.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<CartItem> cartItems;
    @ManyToOne
    private User orderUser;
    private double total;
}
