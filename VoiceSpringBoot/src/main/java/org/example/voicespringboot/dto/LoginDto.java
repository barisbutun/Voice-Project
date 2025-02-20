package org.example.voicespringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link org.example.voicespringboot.entity.User}
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginDto implements Serializable {
    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    @JsonProperty("token")
    private String token;

}