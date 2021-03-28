package de.share_your_idea.user_meeting_search.http_client;

import de.share_your_idea.user_meeting_search.entity.UserEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("http://USER-MANAGEMENT-SERVICE/api/v1/user-management/")
public interface UserManagementServiceHTTPClient {

    @GetMapping("find-all-users")
    ResponseEntity<List<UserEntity>> findAllUserEntities();

    @GetMapping("find-user-by-username/{username}")
    ResponseEntity<UserEntity> findUserEntityByUsername(@PathVariable("username") String username);
}