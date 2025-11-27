package com.example.cem.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "registrations",
        uniqueConstraints = @UniqueConstraint(name="uq_event_student", columnNames={"event_id","student_number"})
)
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_number")
    private User student;

    private LocalDateTime registeredAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.CONFIRMED;

    public enum Status { CONFIRMED, CANCELLED }
}
