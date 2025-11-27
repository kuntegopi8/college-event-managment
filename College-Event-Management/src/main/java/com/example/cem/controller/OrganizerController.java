package com.example.cem.controller;

import com.example.cem.model.Event;
import com.example.cem.model.User;
import com.example.cem.repository.EventRegistrationRepository;
import com.example.cem.repository.EventRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/organizer")
public class OrganizerController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventRegistrationRepository eventRegistrationRepository;

    // ✅ Show all events for this organizer
    @GetMapping("/events")
    public String showAllEvents(Model model, HttpSession session) {
        User organizer = (User) session.getAttribute("loggedInUser");
        if (organizer == null || organizer.getRole() != User.Role.ORGANIZER) {
            return "redirect:/";
        }

        List<Event> events = eventRepository.findByOrganizer(organizer);

        // Count students joined for each event
        Map<Long, Long> eventCounts = new HashMap<>();
        for (Event e : events) {
            long count = eventRegistrationRepository.countByEvent(e);
            eventCounts.put(e.getId(), count);
        }

        model.addAttribute("events", events);
        model.addAttribute("counts", eventCounts);
        return "organizer/events";
    }

    // ✅ Show Add Event Form
    @GetMapping("/add")
    public String showAddEventForm(Model model, HttpSession session) {
        User organizer = (User) session.getAttribute("loggedInUser");
        if (organizer == null || organizer.getRole() != User.Role.ORGANIZER) {
            return "redirect:/";
        }

        model.addAttribute("event", new Event());
        return "organizer/event-form";
    }

    // ✅ Save Event
    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event, HttpSession session) {
        User organizer = (User) session.getAttribute("loggedInUser");
        if (organizer == null || organizer.getRole() != User.Role.ORGANIZER) {
            return "redirect:/";
        }

        event.setOrganizer(organizer);
        eventRepository.save(event);
        return "redirect:/organizer/events";
    }
}
