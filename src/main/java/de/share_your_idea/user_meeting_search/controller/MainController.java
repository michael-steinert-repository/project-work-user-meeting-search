package de.share_your_idea.user_meeting_search.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.share_your_idea.user_meeting_search.entity.SearchQueryEntity;
import de.share_your_idea.user_meeting_search.entity.UserEntity;
import de.share_your_idea.user_meeting_search.entity.UserMeetingEntity;
import de.share_your_idea.user_meeting_search.service.UserMeetingSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
public class MainController {

    @Value("${spring.application.name}")
    private String serviceName;
    private final UserMeetingSearchService userMeetingSearchService;

    @Autowired
    public MainController(UserMeetingSearchService userMeetingSearchService) {
        this.userMeetingSearchService = userMeetingSearchService;
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) throws JsonProcessingException {
        log.info("Main-Controller: Index-Method is called");
        model.addAttribute("serviceName", serviceName);
        log.info("Main-Controller: Index-Method created ServiceName : {}", new ObjectMapper().writeValueAsString(serviceName));
        return "index";
    }

    @GetMapping(value = {"/searchQueryList"})
    public String showSearchQueryList(Model model) throws JsonProcessingException {
        log.info("Main-Controller: ShowSearchQueryList-Method is called");
        List<SearchQueryEntity> searchQueryEntityList = userMeetingSearchService.findAllSearchQueries();
        model.addAttribute("searchQueryEntityList", searchQueryEntityList);
        log.info("Main-Controller: ShowSearchQueryList-Method created UserEntityList : {}", new ObjectMapper().writeValueAsString(searchQueryEntityList));
        return "searchQueryList";
    }

    @GetMapping(value = {"/userList"})
    public String showUserList(Model model) throws JsonProcessingException {
        log.info("Main-Controller: ShowUserList-Method is called");
        List<UserEntity> userEntityList = userMeetingSearchService.findAllUsers();
        model.addAttribute("userEntityList", userEntityList);
        log.info("Main-Controller: ShowUserList-Method created UserEntityList : {}", new ObjectMapper().writeValueAsString(userEntityList));
        return "userList";
    }

    @GetMapping(value = {"/userMeetingList"})
    public String showUserMeetingList(Model model) throws JsonProcessingException {
        log.info("Main-Controller: ShowUserMeetingList-Method is called");
        List<UserMeetingEntity> userMeetingEntityList = userMeetingSearchService.findAllMeetings();
        model.addAttribute("userMeetingEntityList", userMeetingEntityList);
        log.info("Main-Controller: ShowUserMeetingList-Method created UserEntityList : {}", new ObjectMapper().writeValueAsString(userMeetingEntityList));
        return "userMeetingList";
    }
}