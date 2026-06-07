package com.hammad.meetelligence.Repository;

import com.hammad.meetelligence.Entity.MeetingAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingAnalysisRepository extends JpaRepository<MeetingAnalysis, Long> {

    Optional<MeetingAnalysis> findByMeetingId(Long id);
}
