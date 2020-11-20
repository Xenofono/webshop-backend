package tech.kristoffer.webshop.models.requests;

import lombok.Data;

@Data
public class AddToCartRequest {

    private long id;
    private int quantity;

}
