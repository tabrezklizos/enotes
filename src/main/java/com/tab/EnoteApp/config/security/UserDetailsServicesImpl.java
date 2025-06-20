package com.tab.EnoteApp.config.security;

import com.tab.EnoteApp.entity.User;
import com.tab.EnoteApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServicesImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =userRepository.findByEmail(username);

        if(user== null){
            throw new UsernameNotFoundException("invalid email");
        }

        return new CustomUserDetails(user);
    }
}
