package com.example.cem.controller;

import com.example.cem.model.Event;
import com.example.cem.model.EventRegistration;
import com.example.cem.model.User;
import com.example.cem.repository.EventRegistrationRepository;
import com.example.cem.repository.EventRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;

    // 🎓 Show all events
    @GetMapping("/events")
    public String viewAllEvents(Model model, HttpSession session) {
        User student = (User) session.getAttribute("loggedInUser");
        if (student == null) return "redirect:/";
        model.addAttribute("events", eventRepository.findAll());
        return "student/events";
    }

    // 🎫 Show Join Confirmation Form
    @GetMapping("/join/{id}")
    public String showJoinForm(@PathVariable Long id, Model model, HttpSession session) {
        User student = (User) session.getAttribute("loggedInUser");
        if (student == null) return "redirect:/";

        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) return "redirect:/student/events";

        model.addAttribute("event", event);
        model.addAttribute("student", student);
        return "student/join-event"; // ✅ this is your join confirmation form
    }

    // ✅ Handle Confirm Join Submission
    @PostMapping("/register/{id}")
    public String registerForEvent(@PathVariable Long id, HttpSession session) {
        User student = (User) session.getAttribute("loggedInUser");
        if (student == null) return "redirect:/";

        Event event = eventRepository.findById(id).orElse(null);
        if (event == null) return "redirect:/student/events";

        // Check if already joined
        if (!eventRegistrationRepository.existsByStudentAndEvent(student, event)) {
            EventRegistration registration = new EventRegistration(student, event);
            eventRegistrationRepository.save(registration);
        }

        return "redirect:/student/my-events";
    }

    // 📋 My Joined Events
    @GetMapping("/my-events")
    public String viewMyEvents(Model model, HttpSession session) {
        User student = (User) session.getAttribute("loggedInUser");
        if (student == null) return "redirect:/";

        List<EventRegistration> registrations = eventRegistrationRepository.findByStudent(student);
        model.addAttribute("registrations", registrations);
        return "student/my-events";
    }
}
