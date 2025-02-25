package com.example.demo;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AccountDetailsService extends UserDetailsService {
    List<Account> getAllAccounts();
    void saveAccount(Account course);

}
