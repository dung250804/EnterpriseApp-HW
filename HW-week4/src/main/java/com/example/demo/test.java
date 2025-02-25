package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class test {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "123"; // Mật khẩu gốc
        String encodedPassword = passwordEncoder.encode(rawPassword); // Mã hóa mật khẩu

        System.out.println("Mật khẩu gốc: " + rawPassword);
        System.out.println("Mật khẩu đã mã hóa: " + encodedPassword);
    }
}
