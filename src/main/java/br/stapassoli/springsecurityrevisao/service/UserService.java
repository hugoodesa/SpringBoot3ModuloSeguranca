package br.stapassoli.springsecurityrevisao.service;

import br.stapassoli.springsecurityrevisao.dto.UserDTO;
import br.stapassoli.springsecurityrevisao.model.User;
import br.stapassoli.springsecurityrevisao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    public ResponseEntity createUser(UserDTO userDTO){
        String encodePassWord = new BCryptPasswordEncoder().encode(userDTO.getPassword());
        userDTO.setPassword(encodePassWord);

        User savedUser = this.repository.save(new User(userDTO));
        return ResponseEntity.ok(new UserDTO(savedUser));
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return this.repository.findByLogin(login);
    }
}
