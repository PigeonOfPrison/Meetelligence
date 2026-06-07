package com.hammad.meetelligence.Service;


import com.hammad.meetelligence.Entity.Meeting;
import com.hammad.meetelligence.Entity.MeetingAnalysis;
import com.hammad.meetelligence.Entity.TranscriptSegment;
import com.hammad.meetelligence.Repository.MeetingAnalysisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnalysisService {
    private final MeetingService meetingService;
    private final MeetingAnalysisRepository meetingAnalysisRepository;
    private final GroqService groqService;

    @Autowired
    public AnalysisService(MeetingService meetingService, MeetingAnalysisRepository meetingAnalysisRepository, GroqService groqService) {
        this.meetingService = meetingService;
        this.meetingAnalysisRepository = meetingAnalysisRepository;
        this.groqService = groqService;
    }

    public ResponseEntity<String> analyzeMeeting(Long id) {

        MeetingAnalysis analysis = meetingAnalysisRepository.findByMeetingId(id).orElse(null);

        if (analysis != null) {
            return ResponseEntity.ok(analysis.getAnalysisJson());
        }


        Meeting meeting = meetingService.getMeetingById(id).getBody();

        List<TranscriptSegment> transcripts = meeting.getTranscripts();

        String transcriptText =
                transcripts.stream()
                        .map(t ->
                                "[" + t.getTimestamp() + "] "
                                        + t.getSpeaker() + ": "
                                        + t.getText()
                        )
                        .collect(Collectors.joining("\n"));

        String groqRes = groqService.analyzeTranscript(transcriptText);

        MeetingAnalysis newAnalysis = MeetingAnalysis.builder()
                .meeting(meeting)
                .analysisJson(groqRes)
                .build();

        meetingAnalysisRepository.save(newAnalysis);

        return ResponseEntity.ok(groqRes);
    }
}
