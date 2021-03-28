package de.share_your_idea.user_meeting_search.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import de.share_your_idea.user_meeting_search.entity.SearchQueryEntity;
import de.share_your_idea.user_meeting_search.entity.UserEntity;
import de.share_your_idea.user_meeting_search.entity.UserMeetingEntity;
import de.share_your_idea.user_meeting_search.http_client.UserManagementServiceHTTPClient;
import de.share_your_idea.user_meeting_search.http_client.UserMeetingServiceHTTPClient;
import de.share_your_idea.user_meeting_search.repository.SearchQueryEntityRepository;
import de.share_your_idea.user_meeting_search.repository.UserEntityRepository;
import de.share_your_idea.user_meeting_search.repository.UserMeetingEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static de.share_your_idea.user_meeting_search.entity.UserRole.ROLE_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class UserMeetingSearchServiceTest {
    @Mock
    private SearchQueryEntityRepository searchQueryEntityRepository;
    @Mock
    private UserEntityRepository userEntityRepository;
    @Mock
    private UserMeetingEntityRepository userMeetingEntityRepository;
    @Mock
    private UserManagementServiceHTTPClient userManagementServiceHttpClient;
    @Mock
    private UserMeetingServiceHTTPClient userMeetingServiceHttpClient;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<List<UserEntity>> userEntityListArgumentCaptor;
    @Captor
    private ArgumentCaptor<List<UserMeetingEntity>> userMeetingEntityListArgumentCaptor;
    private UserMeetingSearchService userMeetingSearchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userMeetingSearchService = new UserMeetingSearchService(
                searchQueryEntityRepository,
                userEntityRepository,
                userMeetingEntityRepository,
                userManagementServiceHttpClient,
                userMeetingServiceHttpClient);
    }

    @Test
    void itShouldSearchUserEntityBySearchQuery() throws JsonProcessingException {
        /* Given */
        UserEntity userEntity = new UserEntity(1L, "Michael", "testPassword", ROLE_USER, "testAuthorizationToken");
        given(userManagementServiceHttpClient.findAllUserEntities()).willReturn(ResponseEntity.of(Optional.of(List.of(userEntity))));
        given(userEntityRepository.findUserEntityByUsernameContaining(userEntity.getUsername())).willReturn(List.of(userEntity));
        /* When */
        SearchQueryEntity searchQueryEntityFromService = userMeetingSearchService.searchUserEntityBySearchQuery(userEntity.getUsername());
        /* Then */
        /* The Method verify() verifies that the Method findUserEntityByUsernameContaining() is invoked in the Repository */
        /* The Method capture() captures the Value in the Method findUserEntityByUsernameContaining() */
        verify(userEntityRepository).findUserEntityByUsernameContaining(stringArgumentCaptor.capture());
        String usernameArgumentCaptorValue = stringArgumentCaptor.getValue();
        assertThat(usernameArgumentCaptorValue).isEqualTo(userEntity.getUsername());
        //assertThat(searchQueryEntityFromService.getSearchQuery()).isEqualTo(userEntity.getUsername());
        //assertThat(searchQueryEntityFromService.getUserEntityResult()).isEqualTo(List.of(userEntity));
    }

    @Test
    void itShouldSearchUserMeetingEntityBySearchQuery() throws JsonProcessingException {
        /* Given */
        UserMeetingEntity userMeetingEntity = new UserMeetingEntity(1L, "testMeetingName", "testCommunicationLink");
        given(userMeetingServiceHttpClient.findAllUserMeetingEntities()).willReturn(ResponseEntity.of(Optional.of(List.of(userMeetingEntity))));
        given(userMeetingEntityRepository.findUserMeetingEntityByMeetingNameContaining(userMeetingEntity.getMeetingName())).willReturn(List.of(userMeetingEntity));
        /* When */
        SearchQueryEntity searchQueryEntityFromService = userMeetingSearchService.searchUserMeetingEntityBySearchQuery(userMeetingEntity.getMeetingName());
        /* Then */
        /* The Method verify() verifies that the Method findUserMeetingEntityByMeetingNameContaining() is invoked in the Repository */
        /* The Method capture() captures the Value in the Method findUserMeetingEntityByMeetingNameContaining() */
        verify(userMeetingEntityRepository).findUserMeetingEntityByMeetingNameContaining(stringArgumentCaptor.capture());
        String meetingNameArgumentCaptorValue = stringArgumentCaptor.getValue();
        assertThat(meetingNameArgumentCaptorValue).isEqualTo(userMeetingEntity.getMeetingName());
        //assertThat(searchQueryEntityFromService.getSearchQuery()).isEqualTo(userMeetingEntity.getMeetingName());
        //assertThat(searchQueryEntityFromService.getUserMeetingEntityResult()).isEqualTo(List.of(userMeetingEntity));
    }
}