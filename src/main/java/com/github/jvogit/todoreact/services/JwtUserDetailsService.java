package com.github.jvogit.todoreact.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.jvogit.todoreact.entities.accounts.User;
import com.github.jvogit.todoreact.jwt.JwtUserPrincipal;
import com.github.jvogit.todoreact.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String loginUsername)
            throws UsernameNotFoundException {
        User user = userRepository
                .findByUsernameOrEmail(loginUsername, loginUsername)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException(
                            "Unable to find user with id : " + loginUsername);
                });
        return JwtUserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new UsernameNotFoundException(
                    "Unable to find user with id : " + id);
        });

        return JwtUserPrincipal.create(user);
    }

}
