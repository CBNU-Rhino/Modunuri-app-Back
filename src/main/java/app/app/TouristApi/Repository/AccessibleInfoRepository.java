package app.app.TouristApi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import app.app.TouristApi.Entity.AccessibleInfo;

@Repository
public interface AccessibleInfoRepository extends JpaRepository<AccessibleInfo, Long> {
    AccessibleInfo findByContentId(String contentId);
    boolean existsByContentId(String contentId);
}
