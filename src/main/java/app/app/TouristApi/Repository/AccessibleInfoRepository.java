package app.app.TouristApi.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import app.app.TouristApi.Entity.AccessibleInfo;

import java.util.List;

@Repository
public interface AccessibleInfoRepository extends JpaRepository<AccessibleInfo, Long> {

    AccessibleInfo findByContentId(String contentId);

    boolean existsByContentId(String contentId);

    @Query("SELECT ai FROM AccessibleInfo ai JOIN TouristInfo ti ON ai.contentId = ti.contentId " +
            "WHERE (:region IS NULL OR ti.areaCode = :region) " +
            "AND (:sigungu IS NULL OR ti.sigunguCode = :sigungu) " +
            "AND (:contentTypeId IS NULL OR ti.contentTypeId = :contentTypeId) " +
            "AND ( " +
            "(:wheelchair = 'Y' AND ai.wheelchair IS NOT NULL AND ai.wheelchair <> '') " +
            "OR (:elevator = 'Y' AND ai.elevator IS NOT NULL AND ai.elevator <> '') " +
            "OR (:restroom = 'Y' AND ai.restroom IS NOT NULL AND ai.restroom <> '') " +
            "OR (:brailleBlock = 'Y' AND ai.brailleBlock IS NOT NULL AND ai.brailleBlock <> '') " +
            "OR (:stroller = 'Y' AND ai.stroller IS NOT NULL AND ai.stroller <> '') " +
            "OR (:helpDog = 'Y' AND ai.helpDog IS NOT NULL AND ai.helpDog <> '') " +
            ")")
    List<AccessibleInfo> findTouristSpotsByFilters(
            @Param("region") String region,
            @Param("sigungu") String sigungu,
            @Param("contentTypeId") String contentTypeId,
            @Param("wheelchair") String wheelchair,
            @Param("elevator") String elevator,
            @Param("restroom") String restroom,
            @Param("brailleBlock") String brailleBlock,
            @Param("stroller") String stroller,
            @Param("helpDog") String helpDog
    );
}
