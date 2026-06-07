package com.hammad.meetelligence.Repository;

import com.hammad.meetelligence.Entity.ActionItem;
import com.hammad.meetelligence.Entity.ActionItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActionItemRepository extends JpaRepository<ActionItem, Long> {

    public List<ActionItem> findByStatusNotAndDueDateBefore(ActionItemStatus status, LocalDateTime dueDate);
}
