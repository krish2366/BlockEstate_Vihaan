package com.PseudoNerds.LandNFT.Util;

import com.PseudoNerds.LandNFT.Entity.User;
import com.PseudoNerds.LandNFT.Repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtil {


    @Autowired
    private UserRepository userRepository;
    private static final Key SECRET_KEY= Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME=86000000;
    private final Key key = SECRET_KEY;

    public String generateToken(String username,String password){
        User user=userRepository.findByusername(username);
//        User user1=user.get();
        return Jwts.builder()
                .setSubject(user.getOwnerWalletAddress())
//                .setSubject(user1.getPassword())
////                .setSubject(user1.getUsername())
//                .claim("User",user1)
                .claim("wallet", user.getOwnerWalletAddress())
                .claim("username",user.getUsername())
                .claim("userid",user.getId())
                .claim("role",user.getRoles())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }


    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }
    public String extractRole(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        }catch (  IllegalArgumentException e) {

            return false;
        }
    }

}


