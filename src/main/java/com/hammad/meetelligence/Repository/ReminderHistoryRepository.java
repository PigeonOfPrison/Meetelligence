package com.hammad.meetelligence.Repository;

import com.hammad.meetelligence.Entity.ReminderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ReminderHistoryRepository extends JpaRepository<ReminderHistory, Long> {
    boolean existsByActionItemIdAndSentAtAfter(Long id, LocalDateTime sentAtAfter);
}
