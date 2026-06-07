package com.hammad.meetelligence.Service;

import com.hammad.meetelligence.Entity.Meeting;
import com.hammad.meetelligence.Entity.MeetingAnalysis;
import com.hammad.meetelligence.Entity.TranscriptSegment;
import com.hammad.meetelligence.Repository.MeetingAnalysisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnalysisServiceTest {

    @Mock
    private MeetingService meetingService;

    @Mock
    private MeetingAnalysisRepository meetingAnalysisRepository;

    @Mock
    private GroqService groqService;

    @InjectMocks
    private AnalysisService analysisService;

    @Test
    void shouldReturnCachedAnalysis() {

        MeetingAnalysis analysis =
                MeetingAnalysis.builder()
                        .analysisJson("""
                    {
                        "summary":[]
                    }
                    """)
                        .build();

        when(meetingAnalysisRepository.findByMeetingId(1L)).thenReturn(Optional.of(analysis));

        ResponseEntity<String> response = analysisService.analyzeMeeting(1L);

        assertEquals(
                """
                {
                    "summary":[]
                }
                """.trim(),
                response.getBody().trim()
        );

        verify(groqService, never()).analyzeTranscript(anyString());
        verify(meetingAnalysisRepository, never()).save(any());
    }

    @Test
    void shouldGenerateAndSaveAnalysis() {

        TranscriptSegment segment =
                TranscriptSegment.builder()
                        .speaker("John")
                        .timestamp(LocalTime.of(0, 10))
                        .text("Release next Friday")
                        .build();

        Meeting meeting =
                Meeting.builder()
                        .id(1L)
                        .transcripts(List.of(segment))
                        .build();

        when(meetingAnalysisRepository.findByMeetingId(1L))
                .thenReturn(Optional.empty());

        when(meetingService.getMeetingById(1L))
                .thenReturn(ResponseEntity.ok(meeting));

        when(groqService.analyzeTranscript(anyString()))
                .thenReturn("""
                    {
                      "summary":[]
                    }
                    """);

        ResponseEntity<String> response =
                analysisService.analyzeMeeting(1L);

        assertNotNull(response.getBody());

        verify(groqService).analyzeTranscript(anyString());

        verify(meetingAnalysisRepository).save(any(MeetingAnalysis.class));
    }
}
