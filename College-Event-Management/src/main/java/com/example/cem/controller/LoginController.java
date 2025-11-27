package com.example.cem.controller;

import com.example.cem.model.User;
import com.example.cem.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    // Login Handler
    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              @RequestParam String role,
                              HttpSession session,
                              Model model) {

        User user = userRepository.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }

        if (!user.getRole().toString().equals(role)) {
            model.addAttribute("error", "Role mismatch! Please choose the correct role.");
            return "login";
        }

        // ✅ Store user info in session
        session.setAttribute("loggedInUser", user);

        // Redirect according to role
        if (role.equals("ORGANIZER")) {
            return "redirect:/organizer/events";
        } else {
            return "redirect:/student/events";
        }
    }

    // 🚪 Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


}