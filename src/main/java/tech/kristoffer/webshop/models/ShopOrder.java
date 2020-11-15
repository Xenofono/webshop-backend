package tech.kristoffer.webshop.models;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.beans.factory.annotation.Autowired;
import tech.kristoffer.webshop.utilities.JsonMapper;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@TypeDef(name="json", typeClass = JsonStringType.class)
public class ShopOrder {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private Map<String, String> items = new HashMap<>();
    @ManyToOne
    private User user;
    private double total;
    private boolean expedited;


    public void addUser(User user){
        user.getOrders().add(this);
        this.user = user;
    }


}
