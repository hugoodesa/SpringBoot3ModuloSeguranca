package br.stapassoli.springsecurityrevisao.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.logging.Filter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    FilterSecurity filterSecurity;

    @Bean
    public SecurityFilterChain basicConfigSecurity(HttpSecurity http) throws Exception {
        return http
            .csrf()
            .disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeHttpRequests().requestMatchers(HttpMethod.POST,"/login/authenticate").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(filterSecurity, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder configPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
