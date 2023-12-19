package binhtt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tokens")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    @Column(name = "token")
    private String token;
    @Column(name = "token_type")
    private String token_type;
    @Column(name = "expiration_date")
    private  String expiration_date;
    @Column(name = "revoked")
    private String revoked;
    @Column(name = "expired")
    private  String expired;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;
}
