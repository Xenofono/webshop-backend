package tech.kristoffer.webshop.models;

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
    @JoinColumn(name = "user_id")
    private Set<ShopOrder> orders;

    private String username;
    private String password;
    @Transient
    private String authority;
    private int enabled = 1;


}
