package com.hammad.meetelligence.Controller;

import com.hammad.meetelligence.Entity.ActionItem;
import com.hammad.meetelligence.Entity.ActionItemStatus;
import com.hammad.meetelligence.Service.ActionItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/action-items")
public class ActionItemController {

    private final ActionItemService actionItemService;

    @Autowired
    public ActionItemController(ActionItemService actionItemService) {
        this.actionItemService = actionItemService;
    }

    @GetMapping
    public ResponseEntity<List<ActionItem>> findAllActionItems() {
        return actionItemService.getAllActionItems();
    }


    @PostMapping
    public ResponseEntity<ActionItem> createActionItem(@RequestBody ActionItem actionItem) {
        return actionItemService.createActionItem(actionItem);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ActionItem> updateActionItem(@PathVariable Long id, @RequestBody ActionItemStatus status) {
        return actionItemService.updateActionItem(id, status);
    }

    // TODO: Add support for filtering by : status, assignee and/or meetingId
    @GetMapping("/overdue")
    public ResponseEntity<List<ActionItem>> findOverdueActionItems() {
        return actionItemService.findOverdueActionItems();
    }
}
