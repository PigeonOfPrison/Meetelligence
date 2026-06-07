package com.hammad.meetelligence.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hammad.meetelligence.Entity.Meeting;
import com.hammad.meetelligence.Service.AnalysisService;
import com.hammad.meetelligence.Service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {
    private final MeetingService meetingService;
    private final AnalysisService analysisService;

    @Autowired
    public MeetingController(MeetingService meetingService, AnalysisService analysisService) {
        this.meetingService = meetingService;
        this.analysisService = analysisService;
    }

    @GetMapping
    public ResponseEntity<Page<Meeting>> getAllMeetings(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return meetingService.getAllMeetings(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Meeting> getMeetingById(@PathVariable Long id) {
        return meetingService.getMeetingById(id);
    }

    @PostMapping
    public ResponseEntity<Meeting> createMeeting(@RequestBody Meeting meeting) {
        return meetingService.createMeeting(meeting);
    }

    @PostMapping("/{id}/analyze")
    public ResponseEntity<String> analyzeMeeting(@PathVariable Long id) throws JsonProcessingException {
        return analysisService.analyzeMeeting(id);
    }
}
