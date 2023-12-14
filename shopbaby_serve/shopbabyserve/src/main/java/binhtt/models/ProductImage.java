package binhtt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "product_images")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductImage {
    public static final  int MAXIMUM_IMAGE_PER_PRODUCT =5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public   Long Id ;

    @ManyToOne
    @JoinColumn(name = "product_id")
    public Product product;

    @Column(name = "image_url",length = 300)
    public  String imageUrl;
}
