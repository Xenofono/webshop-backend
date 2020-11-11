package tech.kristoffer.webshop.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;
    private int quantity;
}
