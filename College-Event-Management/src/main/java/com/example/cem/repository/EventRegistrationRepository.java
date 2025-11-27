package com.example.cem.repository;

import com.example.cem.model.Event;
import com.example.cem.model.EventRegistration;
import com.example.cem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {

    boolean existsByStudentAndEvent(User student, Event event);

    List<EventRegistration> findByStudent(User student);

    @Query("SELECT COUNT(er) FROM EventRegistration er WHERE er.event = :event")
    long countByEvent(Event event);
}
