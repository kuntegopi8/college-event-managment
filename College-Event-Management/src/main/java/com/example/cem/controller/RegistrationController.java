package com.example.cem.controller;

import com.example.cem.model.User;
import com.example.cem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    // ✅ फक्त registration page दाखवण्यासाठी
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; // templates/register.html
    }

    // ✅ फक्त STUDENT user register करू शकतो
    @PostMapping("/register")
    public String handleRegistration(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String department,
            @RequestParam String branch,
            @RequestParam String year,
            @RequestParam String college
    ) {
        // जर email आधीच वापरला असेल तर error द्या
        if (userRepository.findByEmail(email) != null) {
            return "redirect:/register?error=exists";
        }

        // फक्त student तयार करा
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setDepartment(department);
        user.setBranch(branch);
        user.setYear(year);
        user.setCollege(college);
        user.setRole(User.Role.STUDENT);  // ✅ Fix: फक्त STUDENT role
        user.setNumber(System.currentTimeMillis());

        userRepository.save(user);
        return "redirect:/?registered=true"; // login page वर परत जा
    }
}
