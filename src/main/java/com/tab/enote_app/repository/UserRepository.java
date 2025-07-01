package com.tab.enote_app.repository;

import com.tab.enote_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {

    boolean existsByEmail(String email);

    User findByEmail(String username);

    ;
}
