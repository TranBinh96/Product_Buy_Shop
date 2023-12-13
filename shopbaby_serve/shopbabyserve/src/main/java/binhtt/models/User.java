package binhtt.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long Id;

    @Column(name = "fullname")
    private  String fullname;

    @Column(name = "phone_number",nullable = false)
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "is_active")
    private  int isActive;

    @Column(name = "date_of_birth")
    private LocalDateTime dateOfBirth;

    @Column(name = "facebook_account_id")
    private  int   facebookAccountId;
    @Column(name = "google_account_id")
    private  int googleAccountId;

    @OneToMany
    @JoinColumn(name = "role_id")
    private  Role role;


}
