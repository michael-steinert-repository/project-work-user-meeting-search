package de.share_your_idea.user_meeting_search.repository;

import de.share_your_idea.user_meeting_search.entity.SearchQueryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchQueryEntityRepository extends JpaRepository<SearchQueryEntity, Long> {
}
