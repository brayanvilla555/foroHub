package com.app.appforo.services.security;

import com.app.appforo.dto.security.LoginRequest;
import com.app.appforo.dto.security.LoginResponse;
import com.app.appforo.persistence.entity.User;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl  implements AuthenticationService {

    @Autowired
    private HttpSecurity httpSecurity;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.username());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, loginRequest.password(), user.getAuthorities()
        );

        authenticationManager.authenticate(authentication);

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new LoginResponse(jwt);
    }

    private Map<String, Object> generateExtraClaims(UserDetails user){
        Map<String, Object> extraClaims = new HashMap<>();

        String roleName = ((User) user).getRole().getName().name();

        extraClaims.put("role", roleName);
        extraClaims.put("Authorization", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList())
        );
        return extraClaims;
    }

    @Override
    public void logaut() throws Exception {
        httpSecurity.logout(config ->{
            config.deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true);
        });
    }
}
