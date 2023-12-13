package binhtt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "social_accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;
    @Column(name = "provider")
    private  String provider;

    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "email",nullable = false)
    private  String email;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name = "user_id")
    private  User user;
}

