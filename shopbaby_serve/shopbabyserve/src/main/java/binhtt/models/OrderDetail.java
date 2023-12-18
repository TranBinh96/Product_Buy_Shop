package binhtt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private  Order order;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private  Product product;

    @Column(name = "price")
    private  float price;

    @Column(name = "number_of_products")
    private int numberOfProducts;

    @Column(name = "color")
    private String color;


}
