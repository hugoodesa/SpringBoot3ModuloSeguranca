package br.stapassoli.springsecurityrevisao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TokenDTO {

    private String token;

    public TokenDTO(String token) {
        this.token = token;
    }
}
