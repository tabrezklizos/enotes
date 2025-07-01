package com.tab.enote_app.service;

import com.tab.enote_app.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

   public String generateToken(User user);
   public String extractUsername(String token);
   public Boolean validateToken(String token, UserDetails userDetails);

}
