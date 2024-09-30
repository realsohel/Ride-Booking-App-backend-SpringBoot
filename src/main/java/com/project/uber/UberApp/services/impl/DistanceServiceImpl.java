package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.services.DistanceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
@Slf4j
public class DistanceServiceImpl implements DistanceService {
    private static final String OSRM_API_BASE_URL = "http://router.project-osrm.org/route/v1/driving/";
    private static final Logger logger = LoggerFactory.getLogger(DistanceServiceImpl.class);

    @Override
    public double calculateDistance(Point src, Point des) {

        try{
            String uri = src.getX()+","+src.getY()+";"+des.getX()+","+des.getY();
            System.out.println("OSRM API Request URI: " +OSRM_API_BASE_URL + uri);
            OSRMResponseDto responseDto = RestClient.builder()
                    .baseUrl(OSRM_API_BASE_URL)
                    .build()
                    .get()
                    .uri(uri)
                    .retrieve()
                    .body(OSRMResponseDto.class);

            return responseDto.getRoutes().get(0).getDistance() / 1000.0;
        }
        catch (Exception e){
            logger.error("Error fetching OSRM API: ", e);
            throw new RuntimeException("Error fetching OSRM API : " + e.getMessage());
        }


    }

}

@Data
class OSRMResponseDto{
    List<OSRMRoute> routes;
}

@Data
class OSRMRoute{
    private Double distance;
}
    //    @Override
//    public double calculateDistance(Point src, Point des) {
//        try{
//            String uri = src.getX()+","+src.getY()+";"+des.getX()+","+des.getY();
//            OSRMResponseDto responseDto = RestClient.builder()
//                    .baseUrl(OSRM_API_BASE_URL)
//                    .build()
//                    .get()
//                    .uri(uri)
//                    .retrieve()
//                    .body(OSRMResponseDto.class);
//            return responseDto.getRoutes().get(0).getDistance()/1000.0;
//        }catch (Exception e){
//            throw new RuntimeException("Error fetching OSRM API : " + e.getMessage());
//        }
//
//
//    }
//}
//@Data
//class OSRMResponseDto{
//    private List<OSRMRoutes> routes;
//}
//
//@Data
//class OSRMRoutes{
//    private Double distance;
//}
