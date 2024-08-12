package app.app.TouristApi.Repository;

import app.app.TouristApi.Entity.AreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<AreaEntity, String> {
}
