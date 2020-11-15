package tech.kristoffer.webshop.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Cart cart = new Cart();
    @OneToMany(mappedBy = "user")
    private Set<ShopOrder> orders = new HashSet<>();

    private String username;
    private String password;
    @Transient
    private String authority;
    private int enabled = 1;

    @PrePersist
    public void init(){
        this.cart.setUser(this);
    }

    public void addCartItem(CartItem newItem){
        newItem.setSum(newItem.getProduct().getPrice() * newItem.getQuantity());
        this.cart.getCartItems().add(newItem);
    }


}
