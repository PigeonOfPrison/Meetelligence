package com.hammad.meetelligence.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reminder_history")
@Entity
public class ReminderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime sentAt;

    private Boolean success;


    @ManyToOne
    @JoinColumn(name = "action_item_id")
    private ActionItem actionItem;
}
