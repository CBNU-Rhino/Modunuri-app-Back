package app.app.TouristApi.Service;

import app.app.TouristApi.DTO.TouristResponseDTO;
import app.app.TouristApi.Entity.TouristInfo;
import app.app.TouristApi.Repository.TouristInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TouristInfoService {

    private final RestTemplate restTemplate;
    private final TouristInfoRepository touristInfoRepository;

    public TouristInfoService(RestTemplate restTemplate, TouristInfoRepository touristInfoRepository) {
        this.restTemplate = restTemplate;
        this.touristInfoRepository = touristInfoRepository;
    }

}