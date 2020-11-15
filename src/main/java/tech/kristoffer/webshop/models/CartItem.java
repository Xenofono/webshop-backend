package tech.kristoffer.webshop.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;
    private int quantity;
    private double sum;


    public void addItemAndQuantity(Product product, int quantity){
        this.sum = product.getPrice() * quantity;

    }


}
