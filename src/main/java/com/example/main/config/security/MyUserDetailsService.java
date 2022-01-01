package com.example.main.config.security;

import com.example.main.config.security.users.UserRole;
import com.example.main.model.User;
import com.example.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findByUsername(username);
        if (CollectionUtils.isEmpty(users)) {
            throw new UsernameNotFoundException("Username not found: " + username);
        }
        User user = users.get(0);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), toAuthorities(user));
    }

    private List<SimpleGrantedAuthority> toAuthorities(User user) {
        return getRoles(user.getRole())
                .map(UserRole::toApplicationRole)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private Stream<UserRole> getRoles(UserRole role) {
        if (role.isAdmin()) {
            return Arrays.stream(UserRole.values());
        }
        return Stream.of(role);
    }

}
