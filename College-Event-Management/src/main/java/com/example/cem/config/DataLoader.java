package com.example.cem.config;

import com.example.cem.model.*;
import com.example.cem.repository.*;
import com.example.cem.model.Event;
import com.example.cem.model.User;
import com.example.cem.repository.EventRepository;
import com.example.cem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.time.LocalTime;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(UserRepository users, EventRepository events) {
        return args -> {
            // Organizer
            var org = new User();
            org.setNumber(2001L);
            org.setName("Organizer One");
            org.setEmail("org1@cem.com");
            org.setPassword("123456");
            org.setRole(User.Role.ORGANIZER);
            org.setDepartment("CSE");
            users.save(org);

            // Student
            var stud = new User();
            stud.setNumber(101L);
            stud.setName("Student One");
            stud.setEmail("stud1@cem.com");
            stud.setPassword("123456");
            stud.setRole(User.Role.STUDENT);
            stud.setDepartment("IT");
            users.save(stud);

            // Event
            var e1 = new Event();
            e1.setTitle("Tech Fest 2025");
            e1.setDescription("CSE Department Annual Tech Festival");
            e1.setEventDate(LocalDate.now().plusDays(10));
            e1.setEventTime(LocalTime.of(10, 0));
            e1.setVenue("Auditorium");
            e1.setCapacity(100);
            e1.setDepartment("CSE");
            e1.setOrganizer(org);
            events.save(e1);
        };
    }
}
