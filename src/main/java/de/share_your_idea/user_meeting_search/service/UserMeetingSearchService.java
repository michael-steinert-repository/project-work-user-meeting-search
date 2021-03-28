package de.share_your_idea.user_meeting_search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.share_your_idea.user_meeting_search.entity.SearchQueryEntity;
import de.share_your_idea.user_meeting_search.entity.UserEntity;
import de.share_your_idea.user_meeting_search.entity.UserMeetingEntity;
import de.share_your_idea.user_meeting_search.exception.CustomNotFoundException;
import de.share_your_idea.user_meeting_search.http_client.UserManagementServiceHTTPClient;
import de.share_your_idea.user_meeting_search.http_client.UserMeetingServiceHTTPClient;
import de.share_your_idea.user_meeting_search.repository.SearchQueryEntityRepository;
import de.share_your_idea.user_meeting_search.repository.UserEntityRepository;
import de.share_your_idea.user_meeting_search.repository.UserMeetingEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserMeetingSearchService {
    private final SearchQueryEntityRepository searchQueryEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final UserMeetingEntityRepository userMeetingEntityRepository;
    private final UserManagementServiceHTTPClient userManagementServiceHttpClient;
    private final UserMeetingServiceHTTPClient userMeetingServiceHttpClient;

    @Autowired
    public UserMeetingSearchService(SearchQueryEntityRepository searchQueryEntityRepository,
                                    UserEntityRepository userEntityRepository,
                                    UserMeetingEntityRepository userMeetingEntityRepository,
                                    UserManagementServiceHTTPClient userManagementServiceHttpClient,
                                    UserMeetingServiceHTTPClient userMeetingServiceHttpClient) {
        this.searchQueryEntityRepository = searchQueryEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.userMeetingEntityRepository = userMeetingEntityRepository;
        this.userManagementServiceHttpClient = userManagementServiceHttpClient;
        this.userMeetingServiceHttpClient = userMeetingServiceHttpClient;
    }

    /*
    TODO:
    Add Method to retrieve UserEntities from User-Management-Service to display the Search-Result.
    The retrieved Data should be stored in a temporary Data-Structure (for Example a Map with Date and corresponding Data),
    if the User-Management-Service is not available for the next Search-Query, the last retrieved Result can be displayed instead.
    */

    public SearchQueryEntity searchUserEntityBySearchQuery(String searchQuery) throws JsonProcessingException, CustomNotFoundException {
        log.info("UserMeetingSearch-Service: SearchUserEntityBySearchQuery-Method is called");
        if(!searchQuery.isBlank()) {
            /*
            Request Data from another Service - Microservice UserManagementService.
            */
            ResponseEntity<List<UserEntity>> responseEntityList = userManagementServiceHttpClient.findAllUserEntities();
            /*
            Check if the Request to the other Service has delivered Data, otherwise an Exception is handled.
            */
            if (responseEntityList != null && responseEntityList.hasBody()) {
                List<UserEntity> userEntityList = responseEntityList.getBody();
                /*
                Server-side Caching:
                    Here the List<UserEntity> is cached to ensure that even if the Microservice UserManagement is not available,
                    or the List<UserEntity> is the same, the existing List<UserEntity> is returned from the Repository.
                    This increases the Resilience and Performance of this Service.
                */
                List<UserEntity> userEntityListFromRepository = userEntityRepository.findAll();
                if(!Objects.equals(userEntityList, userEntityListFromRepository)) {
                    userEntityRepository.deleteAll();
                    userEntityList = userEntityRepository.saveAll(userEntityList);
                }
                /*
                Searching UserEntity in the own Repository with SearchQuery
                */
                List<UserEntity> searchQueryResult = userEntityRepository.findUserEntityByUsernameContaining(searchQuery);
                SearchQueryEntity searchQueryEntity = new SearchQueryEntity(searchQuery);
                searchQueryEntity.setUserEntityResult(searchQueryResult);
                searchQueryEntity = searchQueryEntityRepository.save(searchQueryEntity);
                log.info("UserMeetingSearch-Service: SearchUserEntityBySearchQuery-Method fetched UserEntityList : {}", new ObjectMapper().writeValueAsString(userEntityList));
                log.info("UserMeetingSearch-Service: SearchUserEntityBySearchQuery-Method founded SearchQueryResult : {}", new ObjectMapper().writeValueAsString(searchQueryResult));
                log.info("UserMeetingSearch-Service: SearchUserEntityBySearchQuery-Method saved SearchQueryEntity : {}", new ObjectMapper().writeValueAsString(searchQueryEntity));
                return searchQueryEntity;
            }
        }
        throw new CustomNotFoundException(String.format("UserEntity with Username like %s not found.", searchQuery));
    }

    public SearchQueryEntity searchUserMeetingEntityBySearchQuery(String searchQuery) throws JsonProcessingException, CustomNotFoundException {
        log.info("UserMeetingSearch-Service: SearchUserMeetingEntityBySearchQuery-Method is called");
        /*
        Request Data from another Service - Microservice UserMeetingService.
        */
        ResponseEntity<List<UserMeetingEntity>> responseEntity = userMeetingServiceHttpClient.findAllUserMeetingEntities();
        /*
        Check if the Request to the other Service has delivered Data, otherwise an Exception is handled.
        */
        if (responseEntity != null && responseEntity.hasBody()) {
            List<UserMeetingEntity> userMeetingEntityList = responseEntity.getBody();
          /*
            Server-side Caching:
                Here the List<UserMeetingEntity> is cached to ensure that even if the Microservice UserMeeting is not available,
                or the List<UserMeetingEntity> is the same, the existing List<UserMeetingEntity> is returned from the Repository.
                This increases the Resilience and Performance of this Service.
            */
            List<UserMeetingEntity> userMeetingEntityListFromRepository = userMeetingEntityRepository.findAll();
            if(!Objects.equals(userMeetingEntityList, userMeetingEntityRepository)) {
                userMeetingEntityRepository.deleteAll();
                userMeetingEntityList = userMeetingEntityRepository.saveAll(userMeetingEntityList);
            }
            /*
            Searching UserMeetingEntity in the own Repository with SearchQuery
            */
            List<UserMeetingEntity> searchQueryResult = userMeetingEntityRepository.findUserMeetingEntityByMeetingNameContaining(searchQuery);
            SearchQueryEntity searchQueryEntity = new SearchQueryEntity(searchQuery);
            searchQueryEntity.setUserMeetingEntityResult(searchQueryResult);
            searchQueryEntityRepository.save(searchQueryEntity);
            log.info("UserMeetingSearch-Service: SearchUserMeetingEntityBySearchQuery-Method fetched UserMeetingEntityList : {}", new ObjectMapper().writeValueAsString(userMeetingEntityList));
            log.info("UserMeetingSearch-Service: SearchUserMeetingEntityBySearchQuery-Method founded SearchQueryResult : {}", new ObjectMapper().writeValueAsString(searchQueryResult));
            log.info("UserMeetingSearch-Service: SearchUserMeetingEntityBySearchQuery-Method saved SearchQueryEntity : {}", new ObjectMapper().writeValueAsString(searchQueryEntity));
            return searchQueryEntity;
        }
        throw new CustomNotFoundException(String.format("UserMeetingEntity with MeetingName like %s not found.", searchQuery));
    }

    public UserEntity findUserByUsername(String username) throws JsonProcessingException, CustomNotFoundException {
        log.info("User-Meeting-Search-Service: FindUserByUsername-Method is called");
        if (!username.isBlank()) {
            ResponseEntity<UserEntity> responseEntity = userManagementServiceHttpClient.findUserEntityByUsername(username);
            if (responseEntity != null && responseEntity.hasBody()) {
                UserEntity userEntity = responseEntity.getBody();
                Long result = userEntityRepository.deleteUserEntityByUsername(userEntity.getUsername());
                log.info("User-Meeting-Search-Service: FindUserByUsername-Method deleted UserEntity with Result : {}", new ObjectMapper().writeValueAsString(result));
                userEntityRepository.save(userEntity);
                return userEntity;
            }
        }
        throw new CustomNotFoundException(String.format("UserEntity with Username %s not found.", username));
    }

    public List<SearchQueryEntity> findAllSearchQueries() {
        log.info("UserMeetingSearch-Service: FindAllSearchQueries-Method is called");
        return searchQueryEntityRepository.findAll();
    }

    public List<UserEntity> findAllUsers() {
        log.info("UserMeetingSearch-Service: FindAllUsers-Method is called");
        return userEntityRepository.findAll();
    }

    public List<UserMeetingEntity> findAllMeetings() {
        log.info("UserMeetingSearch-Service: FindAllMeetings-Method is called");
        return userMeetingEntityRepository.findAll();
    }
}
