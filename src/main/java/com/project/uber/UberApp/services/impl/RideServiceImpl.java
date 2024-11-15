package com.project.uber.UberApp.services.impl;

import com.project.uber.UberApp.entities.DriverEntity;
import com.project.uber.UberApp.entities.RideEntity;
import com.project.uber.UberApp.entities.RideRequestEntity;
import com.project.uber.UberApp.entities.RiderEntity;
import com.project.uber.UberApp.entities.enums.RideRequestStatus;
import com.project.uber.UberApp.entities.enums.RideStatus;
import com.project.uber.UberApp.exceptions.ResourceNotFoundException;
import com.project.uber.UberApp.repositories.RideRepository;
import com.project.uber.UberApp.services.EmailSenderService;
import com.project.uber.UberApp.services.RideRequestService;
import com.project.uber.UberApp.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;
    private final RideRequestService rideRequestService;
    private final ModelMapper modelMapper;
    private final EmailSenderService emailSenderService;

    @Override
    public RideEntity getRideById(Long rideId) {
        return rideRepository.findById(rideId)
                .orElseThrow(()->new ResourceNotFoundException("Ride not found with id "+ rideId));
    }

    @Override
    public RideEntity createNewRide(RideRequestEntity rideRequestEntity, DriverEntity driverEntity) {
        rideRequestEntity.setRideRequestStatus(RideRequestStatus.CONFIRMED);

        RideEntity ride = modelMapper.map(rideRequestEntity,RideEntity.class);

        ride.setRideStatus(RideStatus.CONFIRMED);
        ride.setDriver(driverEntity);
        ride.setOtp(generateOtp());
        ride.setId(null);

        rideRequestService.update(rideRequestEntity);

        emailSenderService.sendEmail(rideRequestEntity.getRider().getUser().getEmail(),
                "Your ride have been accepted !!",
                "Dear user, \n Your ride have been accepted by our driver-partner "+driverEntity.getUser().getName() +
                        "with the vehicle no. " + driverEntity.getVehicleId() + ". The driver will reach to your source location in a moment."+
                        "Your 4-digit OTP is: "+ ride.getOtp() +" Be careful don't share the OTP with anyone except the Authorized Driver."+
                        "The fare of your ride is Rs." + rideRequestEntity.getFare() +"\n All the best for your ride and Happy Journey.");



        return rideRepository.save(ride);
    }


    @Override
    public RideEntity updateRideStatus(RideEntity rideEntity, RideStatus rideStatus) {
        rideEntity.setRideStatus(rideStatus);
        return rideRepository.save(rideEntity);
    }

    @Override
    public Page<RideEntity> getAllRidesOfRider(RiderEntity rider, PageRequest pageRequest) {

        return rideRepository.findByRider(rider, pageRequest);
    }

    @Override
    public Page<RideEntity> getAllRidesOfDriver(DriverEntity driver, PageRequest pageRequest) {
        return rideRepository.findByDriver(driver, pageRequest);
    }

    private String generateOtp(){
        Random random = new Random();
        int intOtp = random.nextInt(10000); // 0 to 9999

        return String.format("%04d", intOtp);
    }
}
