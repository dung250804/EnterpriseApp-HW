package com.example.demo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AccountController {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountController(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name, @RequestParam String password, Model model) {
        // Kiểm tra nếu username đã tồn tại
        if (accountRepository.findByName(name).isPresent()) {
            model.addAttribute("error", "Tên người dùng đã tồn tại!");
            return "login"; // Quay lại trang login nếu lỗi
        }

        // Mã hóa mật khẩu trước khi lưu vào database
        String hashedPassword = passwordEncoder.encode(password);

        Account newUser = new Account();
        newUser.setName(name);
        newUser.setPassword(hashedPassword); // Lưu mật khẩu đã mã hóa
        newUser.setPosition("USER"); // Mặc định là USER

        // Lưu vào database
        accountRepository.save(newUser);
        model.addAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
        return "login"; // Quay lại trang login sau khi đăng ký thành công
    }

    @PostMapping("/update-password")
    public String updatePassword(@RequestParam String name, @RequestParam String newPassword, Model model) {
        // Kiểm tra xem user có tồn tại không
        Account user = accountRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("Tìm thấy user: " + user.getName());
        System.out.println("Mật khẩu cũ: " + user.getPassword());

        // Mã hóa mật khẩu mới
        String hashedPassword = passwordEncoder.encode(newPassword);
        System.out.println("Mật khẩu mới sau khi hash: " + hashedPassword);

        // Cập nhật mật khẩu mới vào database
        user.setPassword(hashedPassword);
        accountRepository.save(user);

        // Kiểm tra lại sau khi lưu
        Account updatedUser = accountRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("Mật khẩu trong database sau khi cập nhật: " + updatedUser.getPassword());

        model.addAttribute("success", "Cập nhật mật khẩu thành công!");
        return "login"; // Chuyển về trang login
    }


}
