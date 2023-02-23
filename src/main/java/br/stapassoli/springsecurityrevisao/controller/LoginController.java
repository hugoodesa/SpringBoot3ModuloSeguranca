package br.stapassoli.springsecurityrevisao.controller;

import br.stapassoli.springsecurityrevisao.dto.TokenDTO;
import br.stapassoli.springsecurityrevisao.dto.UserDTO;
import br.stapassoli.springsecurityrevisao.service.TokenService;
import br.stapassoli.springsecurityrevisao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService service;

    @Autowired
    AuthenticationManager manager;

    @Autowired
    TokenService tokenService;

    @GetMapping
    public ResponseEntity defaultRoute(){
        return ResponseEntity.ok("Main route");
    }

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UserDTO userDTO){
        return this.service.createUser(userDTO);
    }

    @PostMapping("/authenticate")
    public ResponseEntity authentication(@RequestBody UserDTO userDTO){
        var token = new UsernamePasswordAuthenticationToken(userDTO.getLogin(), userDTO.getPassword());
        Authentication authenticate = this.manager.authenticate(token);

        String tokenJWT = this.tokenService.generateToken(userDTO);

        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

}
