package com.app.appforo.services.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${security.jwt.expitation-minutes}")
    private long expitationMinutes;

    @Value("${security.jwt.secret-key}")
    private String secretKet;

    public String generateToken(UserDetails user, Map<String, Object> extrClaims) {

        Date issuedAt = new Date(System.currentTimeMillis());
        Date expirationAt = new Date(issuedAt.getTime() + expitationMinutes * 60 * 1000);

        return Jwts.builder()
                .claims(extrClaims)
                .subject(user.getUsername())
                .issuedAt(issuedAt)
                .expiration(expirationAt)
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generateKey(){
        byte [] keyBytes = Decoders.BASE64.decode(secretKet);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //esto se crea despues del filter
    public String extraerSubject(String jwt) {
        return extraerClaims(jwt).getSubject();
    }

    private Claims extraerClaims(String jwt) {
        return Jwts.parser().setSigningKey(generateKey()).build()
                .parseClaimsJws(jwt).getBody();
    }
}
