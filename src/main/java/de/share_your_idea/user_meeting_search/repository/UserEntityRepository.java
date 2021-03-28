package de.share_your_idea.user_meeting_search.repository;

import de.share_your_idea.user_meeting_search.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findUserEntityByUsernameContaining(String username);

    @Modifying
    @Transactional(readOnly = false)
    Long deleteUserEntityByUsername(String username);
}
