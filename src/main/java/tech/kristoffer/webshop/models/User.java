package tech.kristoffer.webshop.models;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@SecondaryTable(name = "authority", pkJoinColumns = @PrimaryKeyJoinColumn(name = "id"))
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
    @Column(table = "authority", name="authority")
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
