package de.share_your_idea.user_meeting_search.repository;

import de.share_your_idea.user_meeting_search.entity.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

import static de.share_your_idea.user_meeting_search.entity.UserRole.ROLE_USER;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/* Unit-Test for UserMeetingEntityRepository */
/* To trigger the Annotations from UserEntity the following Property is necessary */
/* For Example @Column(nullable = false) */
@DataJpaTest(properties = "spring.jpa.properties.javax.persistence.validation.mode=none")
@PropertySource("classpath:application.yml")
@PropertySource("classpath:bootstrap.yml")
class UserEntityRepositoryTest {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @AfterEach
    void tearDown() {
        userEntityRepository.deleteAll();
    }

    @Test
    void itShouldSearchMeetingEntityBySearchQuery() {
        /* Given */
        UserEntity userEntity = new UserEntity(1L, "Michael", "testPassword", ROLE_USER, "testAuthorizationToken");
        /* When */
        userEntityRepository.save(userEntity);
        /* Then */
        List<UserEntity> userEntityList = userEntityRepository.findUserEntityByUsernameContaining(userEntity.getUsername());
        assertThat(userEntityList.isEmpty()).isFalse();
        assertThat(userEntityList).isEqualTo(List.of(userEntity));
    }
}