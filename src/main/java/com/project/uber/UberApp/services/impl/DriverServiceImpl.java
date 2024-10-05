package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.dto.DriverDto;
import com.project.uber.UberApp.dto.RideDto;
import com.project.uber.UberApp.dto.RiderDto;
import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.RideRequestEntity;
import com.project.uber.UberApp.entities.enums.RideRequestStatus;
import com.project.uber.UberApp.entities.enums.RideStatus;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.repositories.DriverRepository;
import com.project.uber.UberApp.services.DriverService;
import com.project.uber.UberApp.services.PaymentService;
import com.project.uber.UberApp.services.RideRequestService;
import com.project.uber.UberApp.services.RideService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;
    @Override
    @Transactional
    public RideDto acceptRide(Long rideRequestId) {
        RideRequestEntity rideRequestEntity= rideRequestService.findRideRequestById(rideRequestId);

        if(!rideRequestEntity.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride Request cannot be accepted, status is "+ rideRequestEntity.getRideRequestStatus());
        }

        DriverEntity currentDriver= getCurrentDriver();

        if(!currentDriver.getAvailable()){
            throw new RuntimeException("Driver cannot accept the ride at this moment due to unavailabilty ");
        }

        updateDriverAvailability(currentDriver, false);
        RideEntity rideEntity = rideService.createNewRide(rideRequestEntity,currentDriver);

        return modelMapper.map(rideEntity,RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        RideEntity ride = rideService.getRideById(rideId);

        DriverEntity driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot cancel the ride as he has not accpeted it earlier");
        }

        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be cancelled as the status of the ride is not CONFIRMED, the status is : "+ ride.getRideStatus() );
        }

        rideService.updateRideStatus(ride, RideStatus.CANCELLED);
        updateDriverAvailability(ride.getDriver(), true);

        return modelMapper.map(ride,RideDto.class);
    }

    @Override
    public RideDto startRide(Long rideId , String otp) {
        RideEntity ride = rideService.getRideById(rideId);

        DriverEntity driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start the ride as he has not accpeted it earlier");
        }
        if(!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("Ride cannot be started as the status of the ride is not CONFIRMED, the status is : "+ ride.getRideStatus()  );
        }
        if(!otp.equals(ride.getOtp())){
            throw new RuntimeException("Incorrect Otp ! Please enter a valid otp. Given otp : " + otp);
        }
        ride.setStartedAt(LocalDateTime.now());
        RideEntity savedRide  = rideService.updateRideStatus(ride, RideStatus.ONGOING);
        paymentService.createNewPayment(savedRide);

        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        RideEntity ride = rideService.getRideById(rideId);
        DriverEntity driver = getCurrentDriver();

        if(!driver.equals(ride.getDriver())){
            throw new RuntimeException("Driver cannot start the ride as he has not accpeted it earlier");
        }
        if(!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("Ride cannot be ended as the status of the ride is not ONGOING, the status is : "+ ride.getRideStatus()  );
        }

        ride.setEndedAt(LocalDateTime.now());
        RideEntity savedRide = rideService.updateRideStatus(ride,RideStatus.ENDED);
        updateDriverAvailability(driver,true);

        paymentService.processPayment(ride);
        return modelMapper.map(savedRide,RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long rideId, Integer rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        DriverEntity driver = getCurrentDriver();

        return modelMapper.map(driver, DriverDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(PageRequest pageRequest) {
        DriverEntity driver = getCurrentDriver();

        return  rideService.getAllRidesOfDriver(driver, pageRequest).map(
                ride -> modelMapper.map(ride, RideDto.class)
        );
    }

    @Override
    public DriverEntity getCurrentDriver() {
        return driverRepository.findById(2L)
                .orElseThrow(()-> new ResourceNotFoundException("Driver not found with id : 2"));
    }

    @Override
    public void updateDriverAvailability(DriverEntity driver, boolean available) {
        driver.setAvailable(available);
        driverRepository.save(driver);
    }
}
