package binhtt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "products")
public class Product extends  BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    @Column(name = "name",nullable = false,length = 350)
    private String name ;
    @Column(name = "price")
    private Float price ;

    @Column(name = "thumbnail",length = 350)
    private String thumbnail ;
    @Column(name = "description")
    private String description ;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;





}
