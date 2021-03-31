package de.share_your_idea.user_meeting_search.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.web.servlet.MockMvc;

/* Integration-Test for SearchQueryController */
@AutoConfigureMockMvc
@SpringBootTest
@PropertySource("classpath:application.yml")
@PropertySource("classpath:bootstrap.yml")
@Disabled
public class UserMeetingSearchIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
}
