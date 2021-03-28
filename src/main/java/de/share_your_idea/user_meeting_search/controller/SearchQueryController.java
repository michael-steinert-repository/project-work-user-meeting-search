package de.share_your_idea.user_meeting_search.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.share_your_idea.user_meeting_search.entity.SearchQueryEntity;
import de.share_your_idea.user_meeting_search.entity.UserEntity;
import de.share_your_idea.user_meeting_search.service.UserMeetingSearchService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("api/v1/user-meeting-search")
@RestController
public class SearchQueryController {

    private final UserMeetingSearchService userMeetingSearchService;

    @Autowired
    public SearchQueryController(UserMeetingSearchService userMeetingSearchService) {
        this.userMeetingSearchService = userMeetingSearchService;
    }

    @GetMapping("/search-user/{searchQuery}")
    public ResponseEntity<SearchQueryEntity> searchUser(@PathVariable("searchQuery") String searchQuery) throws JsonProcessingException, NotFoundException {
        log.info("SearchQueryController-Controller: SearchUser-Method is called");
        SearchQueryEntity searchQueryEntity = userMeetingSearchService.searchUserEntityBySearchQuery(searchQuery);
        log.info("SearchQueryController-Controller: SearchUser-Method founded SearchQueryEntity : {}", new ObjectMapper().writeValueAsString(searchQueryEntity));
        return new ResponseEntity<>(searchQueryEntity, HttpStatus.OK);
    }

    @GetMapping("/search-meeting/{searchQuery}")
    public ResponseEntity<SearchQueryEntity> searchMeeting(@PathVariable("searchQuery") String searchQuery) throws JsonProcessingException, NotFoundException {
        log.info("SearchQueryController-Controller: SearchMeeting-Method is called");
        SearchQueryEntity searchQueryEntity = userMeetingSearchService.searchUserMeetingEntityBySearchQuery(searchQuery);
        log.info("SearchQueryController-Controller: searchMeeting-Method founded SearchQueryEntity : {}", new ObjectMapper().writeValueAsString(searchQueryEntity));
        return new ResponseEntity<>(searchQueryEntity, HttpStatus.OK);
    }

    @GetMapping(path = "/fetch-user-by-username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntity> fetchUserByUsername(@PathVariable("username") String username) throws JsonProcessingException, NotFoundException {
        log.info("Search-Query-Controller: FetchUserByUsername-Method is called");
        UserEntity userEntity = userMeetingSearchService.findUserByUsername(username);
        log.info("Search-Query-Controller: FetchUserByUsername-Method created UserEntity : {}", new ObjectMapper().writeValueAsString(userEntity));
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}