package com.lessons.java.wdpt6.ticket_platform.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lessons.java.wdpt6.ticket_platform.model.User;
import com.lessons.java.wdpt6.ticket_platform.repo.UserRepo;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
    
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepo.findByUserName(userName);

        if (user.isPresent()) {
            return new DatabaseUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("Username not found");
        }

    }

}
