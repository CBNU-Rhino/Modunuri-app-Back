package app.app.TouristApi.Service;

import app.app.Code.Area;
import app.app.TouristApi.Entity.AreaEntity;
import app.app.TouristApi.Repository.AreaRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @PostConstruct
    public void initAreas() {
        for (Area area : Area.values()) {
            AreaEntity areaEntity = new AreaEntity(
                    area.name(),
                    area.getRegionCode(),
                    area.getSigunguCode(),
                    area.getRegionName(),
                    area.getX(),
                    area.getY()
            );
            areaRepository.save(areaEntity);
        }
    }
}
