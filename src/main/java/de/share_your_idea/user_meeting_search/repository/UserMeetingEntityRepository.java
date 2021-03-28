package de.share_your_idea.user_meeting_search.repository;

import de.share_your_idea.user_meeting_search.entity.UserMeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMeetingEntityRepository extends JpaRepository<UserMeetingEntity, Long> {
    List<UserMeetingEntity> findUserMeetingEntityByMeetingNameContaining(String meetingName);
}
