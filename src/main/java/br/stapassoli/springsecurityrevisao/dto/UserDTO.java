package br.stapassoli.springsecurityrevisao.dto;

import br.stapassoli.springsecurityrevisao.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO  {

    private String login;
    private String password;

    public UserDTO(User user) {
        this.login = user.getLogin();
        this.password = user.getPassword();
    }
}
