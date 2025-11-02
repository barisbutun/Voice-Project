package org.example.voicespringboot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {
    @JsonProperty("email")
    private String email;

    @JsonProperty("name")
    private String name;

    public RegisterDto(String email, String name) {
        this.email = email;
        this.name = name;
    }

}
