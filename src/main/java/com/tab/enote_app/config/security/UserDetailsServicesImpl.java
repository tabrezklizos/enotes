package com.tab.enote_app.config.security;

import com.tab.enote_app.entity.User;
import com.tab.enote_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServicesImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user =userRepository.findByEmail(username);

        if(user== null){
            throw new UsernameNotFoundException("invalid email");
        }

        return new CustomUserDetails(user);
    }
}
