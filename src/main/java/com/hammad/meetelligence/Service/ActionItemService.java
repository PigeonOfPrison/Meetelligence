package com.hammad.meetelligence.Service;

import com.hammad.meetelligence.Entity.ActionItem;
import com.hammad.meetelligence.Entity.ActionItemStatus;
import com.hammad.meetelligence.Error.ResourceNotFoundException;
import com.hammad.meetelligence.Repository.ActionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActionItemService {

    private final ActionItemRepository actionItemRepository;

    @Autowired
    public ActionItemService(ActionItemRepository actionItemRepository) {
        this.actionItemRepository = actionItemRepository;
    }


    public ResponseEntity<List<ActionItem>> getAllActionItems() {
        return ResponseEntity.ok(actionItemRepository.findAll());
    }

    public ResponseEntity<ActionItem> createActionItem(ActionItem actionItem) {
        return ResponseEntity.ok(actionItemRepository.save(actionItem));
    }

    public ResponseEntity<ActionItem> updateActionItem(Long id, ActionItemStatus status) {
        ActionItem item = actionItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Action Item Not Found"));

        item.setStatus(status);
        return ResponseEntity.ok(actionItemRepository.save(item));
    }

    public ResponseEntity<List<ActionItem>> findOverdueActionItems() {
        List<ActionItem> items = findAllOverdueActionItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    public List<ActionItem> findAllOverdueActionItems() {
        return actionItemRepository.findByStatusNotAndDueDateBefore(ActionItemStatus.COMPLETED, LocalDateTime.now());
    }
}
