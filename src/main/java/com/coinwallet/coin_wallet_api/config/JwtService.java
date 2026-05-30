package com.coinwallet.coin_wallet_api.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {


    private static final String SECRET_KEY = "mi_clave_super_secreta_muy_larga_para_que_sea_segura_coinwallet_123456";
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    //generate token method
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) 
                .signWith(key, SignatureAlgorithm.HS256) 
                .compact();
    }

    //method to get email from the token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}