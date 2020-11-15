package tech.kristoffer.webshop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class JsonCartItem {

    @JsonIgnore
    private int id;
    private String productName;
    private int quantity;
    private double sum;

    public JsonCartItem(CartItem cartItem) {
        this.productName = cartItem.getProduct().getName();
        this.quantity = cartItem.getQuantity();
        this.sum = cartItem.getSum();
    }
}
