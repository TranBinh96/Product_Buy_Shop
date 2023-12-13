package binhtt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email")
    private  String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "note")
    private String note;
    @Column(name = "order_date")
    private LocalDateTime order_date;
    @Column(name = "status",length = 20)
    private String status;
    @Column(name = "total_money")
    private Float totalMoney;
    @Column(name = "shipping_method",length = 100)
    private String shippingMethod;

    @Column(name = "shipping_address",length = 100)
    private String shippingAddress;

    @Column(name = "shipping_date")
    private Date shippingDate;

    @Column(name = "tracking_number",length = 100)
    private String trackingNumber;

    @Column(name = "payment_method",length = 100)
    private String paymentMethod;

    @Column(name = "active",length = 1)
    private int active;
}
