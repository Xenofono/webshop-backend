package tech.kristoffer.webshop.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart = new Cart();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<ShopOrder> orders = new HashSet<>();

    private String username;
    private String password;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "authority_id", referencedColumnName = "id")
    @PrimaryKeyJoinColumn
    private Authority authority;
    private int enabled = 1;

    @PrePersist
    public void init(){
        this.cart.setUser(this);
    }

    public void addCartItem(CartItem newItem){
        this.cart.getCartItems().add(newItem);
        double oldTotal = this.cart.getTotal();
        double newTotal = oldTotal + newItem.getSum();
        this.cart.setTotal(newTotal);

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
