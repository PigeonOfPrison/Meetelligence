package com.hammad.meetelligence.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "action_item")
@Entity
public class ActionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String task;

    private String assignee;

    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    private ActionItemStatus status;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;
}
