package binhtt.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class BaseReponse {
    @JsonProperty("create_at")
    public LocalDateTime createAt;

    @JsonProperty("update_at")
    public LocalDateTime updateAt ;
}
