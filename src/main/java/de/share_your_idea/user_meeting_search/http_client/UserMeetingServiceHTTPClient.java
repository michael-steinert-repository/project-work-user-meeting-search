package de.share_your_idea.user_meeting_search.http_client;

import de.share_your_idea.user_meeting_search.entity.UserMeetingEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("http://USER-MEETING-SERVICE/api/v1/user-meeting/")
public interface UserMeetingServiceHTTPClient {

    @GetMapping("find-all-user-meetings")
    ResponseEntity<List<UserMeetingEntity>> findAllUserMeetingEntities();
}
