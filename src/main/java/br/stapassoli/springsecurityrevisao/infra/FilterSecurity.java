package br.stapassoli.springsecurityrevisao.infra;

import br.stapassoli.springsecurityrevisao.repository.UserRepository;
import br.stapassoli.springsecurityrevisao.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class FilterSecurity extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Passed for the filter");
        String subject = "in login step";
        String tokenJWT = recuperarToken(request);

        if(Objects.nonNull(tokenJWT)){
            subject = tokenService.verifyToken(tokenJWT);
            UserDetails user = this.repository.findByLogin(subject);

            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        System.out.println(subject);
        filterChain.doFilter(request,response);
    }

    public String recuperarToken (HttpServletRequest request){

        if(Objects.nonNull(request.getHeader("Authorization"))){
            return request.getHeader("Authorization").replace("Bearer ","");
        }

        if(request.getRequestURI().equals("/login/authenticate")){
            return null;
        }

        throw new RuntimeException("No authorization header was sended");
    }

}
