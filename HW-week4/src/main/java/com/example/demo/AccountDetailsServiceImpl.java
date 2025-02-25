package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder; // Dùng field injection thay vì constructor injection

    public AccountDetailsServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tìm user trong database
        Account user = accountRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Chuyển đổi position thành ROLE_USER hoặc ROLE_ADMIN
        String role = "ROLE_" + user.getPosition().toUpperCase();

        return new User(user.getName(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(role)));
    }
}
