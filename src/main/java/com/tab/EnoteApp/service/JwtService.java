package com.tab.EnoteApp.service;

import com.tab.EnoteApp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

   public String generateToken(User user);
   public String extractUsername(String token);
   public Boolean validateToken(String token, UserDetails userDetails);

}
