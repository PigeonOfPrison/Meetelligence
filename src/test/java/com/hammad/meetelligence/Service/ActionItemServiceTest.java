package com.hammad.meetelligence.Service;

import com.hammad.meetelligence.Entity.ActionItem;
import com.hammad.meetelligence.Entity.ActionItemStatus;
import com.hammad.meetelligence.Error.ResourceNotFoundException;
import com.hammad.meetelligence.Repository.ActionItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionItemServiceTest {
    @Mock
    private ActionItemRepository actionItemRepository;

    @InjectMocks
    private ActionItemService actionItemService;

    @Test
    void shouldReturnOverDueItems() {
        ActionItem item = ActionItem.builder()
                .id(1L)
                .task("Prepare release notes")
                .status(ActionItemStatus.PENDING)
                .dueDate(LocalDateTime.now().minusDays(1))
                .build();

        when(actionItemRepository.
                findByStatusNotAndDueDateBefore(
                        eq(ActionItemStatus.COMPLETED),
                        any(LocalDateTime.class)
                ))
                .thenReturn(List.of(item));

        List<ActionItem> result = actionItemService.findAllOverdueActionItems();

        assertEquals(1, result.size());
        assertEquals("Prepare release notes", result.get(0).getTask());

        verify(actionItemRepository)
                .findByStatusNotAndDueDateBefore(
                        eq(ActionItemStatus.COMPLETED),
                        any(LocalDateTime.class)
                );
    }

    @Test
    void shouldUpdateActionItemStatus() {

        ActionItem item = ActionItem.builder()
                .id(1L)
                .status(ActionItemStatus.PENDING)
                .build();

        when(actionItemRepository.findById(1L))
                .thenReturn(Optional.of(item));

        when(actionItemRepository.save(any(ActionItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        ResponseEntity<ActionItem> response =
                actionItemService.updateActionItem(
                        1L,
                        ActionItemStatus.COMPLETED
                );

        assertEquals(
                ActionItemStatus.COMPLETED,
                response.getBody().getStatus()
        );

        verify(actionItemRepository)
                .save(item);
    }

    @Test
    void shouldThrowWhenActionItemNotFound() {

        when(actionItemRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> actionItemService.updateActionItem(
                        1L,
                        ActionItemStatus.COMPLETED
                )
        );
    }
}
