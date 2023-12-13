package binhtt.models;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {
    @Column(name = "create_at")
    private LocalDateTime create_at;

    @Column(name = "update_at")
    private LocalDateTime update_at ;

    @PrePersist
    public void onCreate() {
        create_at = LocalDateTime.now();
        update_at = LocalDateTime.now();
    }
    @PreUpdate
    public void onUpdate() {
        update_at= LocalDateTime.now();
    }
}
