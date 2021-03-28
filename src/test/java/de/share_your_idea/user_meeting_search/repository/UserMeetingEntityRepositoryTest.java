package de.share_your_idea.user_meeting_search.repository;

import de.share_your_idea.user_meeting_search.entity.UserMeetingEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/* Unit-Test for UserMeetingEntityRepository */
/* To trigger the Annotations from UserEntity the following Property is necessary */
/* For Example @Column(nullable = false) */
@DataJpaTest(properties = "spring.jpa.properties.javax.persistence.validation.mode=none")
@PropertySource("classpath:application.yml")
@PropertySource("classpath:bootstrap.yml")
class UserMeetingEntityRepositoryTest {
    @Autowired
    private UserMeetingEntityRepository userMeetingEntityRepository;

    @Test
    void itShouldSearchMeetingEntityBySearchQuery() {
        /* Given */
        UserMeetingEntity userMeetingEntity = new UserMeetingEntity(1L, "testMeetingName", "testCommunicationLink");
        /* When */
        userMeetingEntityRepository.save(userMeetingEntity);
        /* Then */
        List<UserMeetingEntity> userMeetingEntityList = userMeetingEntityRepository.findUserMeetingEntityByMeetingNameContaining(userMeetingEntity.getMeetingName());
        assertThat(userMeetingEntityList.isEmpty()).isFalse();
        assertThat(userMeetingEntityList).isEqualTo(List.of(userMeetingEntity));
    }
}