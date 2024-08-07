package app.app.user.Repository;

import app.app.user.kakaoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface kakaoUserRepository extends JpaRepository<kakaoUser, Long> {
    // 추가적인 쿼리 메서드를 정의할 수 있습니다.
}