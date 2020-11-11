package tech.kristoffer.webshop.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Cart cart;
    @OneToMany
    private Set<Order> orders;
}
