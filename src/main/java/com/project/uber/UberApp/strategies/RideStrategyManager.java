package com.project.uber.UberApp.strategies;

import com.project.uber.UberApp.strategies.impl.DriverMatchingHighestRatedDriverStrategy;
import com.project.uber.UberApp.strategies.impl.DriverMatchingNearestDriverStrategy;
import com.project.uber.UberApp.strategies.impl.RideFareSurgePricingFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategyManager {

    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStrategy;
    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStrategy;
    private final RideFareCalculationStrategy rideFareCalculationStrategy;
    private final RideFareSurgePricingFareCalculationStrategy rideFareSurgePricingFareCalculationStrategy;

    public DriverMatchingStrategy driverMatchingStrategy(double rating){
        if(rating>=4.5){
            return driverMatchingHighestRatedDriverStrategy;
        }
        else{
            return driverMatchingNearestDriverStrategy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){
//        Creating based on Peak time i.e 6 PM  to 10 PM

        LocalTime surgeStart = LocalTime.of(18,00);
        LocalTime surgeEnd = LocalTime.of(22,00);
        LocalTime currTime = LocalTime.now();

        boolean isSurge = currTime.isAfter(surgeStart) && currTime.isBefore(surgeEnd);

        if(isSurge){
            return rideFareSurgePricingFareCalculationStrategy;
        }else{
            return rideFareCalculationStrategy;
        }
    }
}
