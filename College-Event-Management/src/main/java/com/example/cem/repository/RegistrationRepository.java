package com.example.cem.repository;

import com.example.cem.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByStudent_Number(Long number);
    List<Registration> findByEvent_Id(Long eventId);
}
