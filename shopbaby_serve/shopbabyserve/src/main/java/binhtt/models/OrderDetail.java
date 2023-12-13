package binhtt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
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

    @Column(name = "numberOfProducts")
    private int number_of_products;

    @Column(name = "color")
    private String color;

}
