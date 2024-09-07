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

    @Query("SELECT ai FROM AccessibleInfo ai WHERE ai.contentId IN :contentIdList")
    List<AccessibleInfo> findByContentIdIn(@Param("contentIdList") List<String> contentIdList);

    @Query("SELECT ai FROM AccessibleInfo ai WHERE ai.contentId IN :contentIdList AND (" +
            "(ai.wheelchair IS NOT NULL AND ai.wheelchair <> '' AND 'wheelchair' IN :accessibleTypeList) " +
            "OR (ai.elevator IS NOT NULL AND ai.elevator <> '' AND 'elevator' IN :accessibleTypeList) " +
            "OR (ai.restroom IS NOT NULL AND ai.restroom <> '' AND 'restroom' IN :accessibleTypeList) " +
            "OR (ai.brailleBlock IS NOT NULL AND ai.brailleBlock <> '' AND 'braille_block' IN :accessibleTypeList) " +
            "OR (ai.stroller IS NOT NULL AND ai.stroller <> '' AND 'stroller' IN :accessibleTypeList) " +
            "OR (ai.helpDog IS NOT NULL AND ai.helpDog <> '' AND 'help_dog' IN :accessibleTypeList))")
    List<AccessibleInfo> findByContentIdInAndAccessibleTypes(@Param("contentIdList") List<String> contentIdList,
                                                             @Param("accessibleTypeList") List<String> accessibleTypeList);

}
