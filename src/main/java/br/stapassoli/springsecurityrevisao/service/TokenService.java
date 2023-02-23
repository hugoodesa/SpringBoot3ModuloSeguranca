package br.stapassoli.springsecurityrevisao.service;

import br.stapassoli.springsecurityrevisao.dto.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    @Value("${security-key}")
    private String securityKey;

    public String generateToken(UserDTO userDTO) {

        try {
            return JWT.create()
                    .withIssuer("auth0")
                    .withSubject(userDTO.getLogin())
                    .sign(Algorithm.HMAC256(securityKey));
        } catch (Exception e) {
            throw new RuntimeException("Error to generate TOKEN");
        }
    }

    public String verifyToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(this.securityKey))
                    .withIssuer("auth0")
                    .build().verify(token).getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Its not a valid token");
        }
    }

}
