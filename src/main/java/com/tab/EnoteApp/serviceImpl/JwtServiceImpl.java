package com.tab.EnoteApp.serviceImpl;

import com.tab.EnoteApp.entity.User;
import com.tab.EnoteApp.exception.JwtAuthException;
import com.tab.EnoteApp.exception.JwtExpireException;
import com.tab.EnoteApp.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtServiceImpl implements JwtService {

    private String secretKey="";

    public JwtServiceImpl() {

        try {

            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            secretKey=Base64.getEncoder().encodeToString(sk.getEncoded());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String generateToken(User user){

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRoles());
        claims.put("status", user.getStatus());

        String token = Jwts.builder()
                .claims().add(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60*60*10))
                .and()
                .signWith(getKey())
                .compact();

        return token;
    }
    @Override
    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();

    }
    public String extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return (String) claims.get("roles");
    }

    private Claims extractAllClaims(String token){
        try {
            return  Jwts.parser()
                        .verifyWith(decrypt(secretKey))
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();
        } catch (ExpiredJwtException e) {
            throw new JwtExpireException("Token Expired");
        } catch (JwtException e) {
            throw new JwtAuthException("Invalid Token");
        }
        catch (Exception e) {
            throw e;
        }

    }

    private SecretKey decrypt(String secretKey){
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
    private Key getKey(){
        byte[] key = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        Boolean isExpire =  isTokenExpire(token);
        if(username.equals(userDetails.getUsername()) && !isExpire){
            return true;
        }
        return false;
    }
    private Boolean isTokenExpire(String token) {
        Date isExpire = extractAllClaims(token).getExpiration();
        return isExpire.before(new Date());
    }


}
