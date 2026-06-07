package com.hammad.meetelligence.Service;

import com.hammad.meetelligence.Entity.Meeting;
import com.hammad.meetelligence.Entity.TranscriptSegment;
import com.hammad.meetelligence.Repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    public ResponseEntity<Page<Meeting>> getAllMeetings(int page, int size) {
        // Sorted the meetings in descending order by meetingDates
        Pageable pageable = PageRequest.of(page, size, Sort.by("meetingDate").descending());
        return new ResponseEntity<>(meetingRepository.findAll(pageable), HttpStatus.OK);
    }

    public ResponseEntity<Meeting> getMeetingById(Long id) {
        Meeting meeting = meetingRepository.findById(id).orElse(null);
        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    // TODO: we are directly using the Meeting entity here. This opens a surface for malicious users. Therefore, lets use a DTO for this as well in the future.
    public ResponseEntity<Meeting> createMeeting(Meeting meeting) {
        for (TranscriptSegment transcript : meeting.getTranscripts()) {
            transcript.setMeeting(meeting);
        }

        Meeting savedMeeting = meetingRepository.save(meeting);

        if (savedMeeting != null) {
            return new ResponseEntity<>(savedMeeting, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
