package app.app.TouristApi.Repository;

import app.app.TouristApi.TouristInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristInfoRepository extends JpaRepository<TouristInfo, Long> {
    // 기본적인 CRUD 메서드는 JpaRepository에서 제공됩니다.
    // 필요한 경우, 커스텀 쿼리 메서드를 추가할 수 있습니다.
}
