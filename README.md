# RideOn -  A Ride Booking Spring-Boot Application.

**Motto**: "RideOn to Tension Gone!"

**Overview**: The RideOn application provides a seamless ride-booking experience powered by a robust backend architecture built with Spring Boot. The app supports three types of users – Riders, Drivers, and Admins – and offers essential functionalities for ride management, payment processing, and user authentication. With strategically designed features like driver matching and fare calculation, RideOn aims to deliver efficient, safe, and user-friendly transportation options.

---
## LLD with Flowchart
![LLD of RideOn](https://drive.google.com/uc?export=view&id=1rSZQ3kjxDlQB2T560wscZznBFe3VQxX5)

--- 

## Use Case Diagram - 
[View PDF](https://drive.google.com/file/d/1fsebQ-r-F-D7Ely3nhSUo7JwMI_2xywY/view?usp=drive_link)


## Table of Contents
1. [User Roles and Permissions](#user-roles-and-permissions)
2. [User Authentication and Wallet System](#user-authentication-and-wallet-system)
3. [Ride Management](#ride-management)
4. [Driver Matching and Fare Calculation](#driver-matching-and-fare-calculation)
5. [Ride Workflow](#ride-workflow)
6. [Payment and Wallet Transactions](#payment-and-wallet-transactions)
7. [Rating System](#rating-system)
8. [Enums](#enums)
9. [Testing and Documentation](#testing-and-documentation)

---

### User Roles and Permissions

RideOn supports three primary user roles:

1. **Rider**:
   - Default role for all users upon signup.
   - Can request, cancel rides, access personal profile, view all rides, and rate drivers.

2. **Driver**:
   - Users can be onboarded as drivers only by the Admin.
   - Once onboarded, drivers can accept and start rides, view profiles, and rate riders.
  
3. **Admin**:
   - Responsible for managing the onboarding of drivers and overseeing user activities.
  
Each user role has distinct capabilities, ensuring a clear separation of responsibilities and streamlined operations.

---

### User Authentication and Wallet System

1. **User Authentication**:
   - **Signup** and **Login** features are managed with Spring Security.
   - Each new user signs up with a secure authentication process, creating a personal wallet upon account creation.

2. **Wallet System**:
   - The application offers an integrated wallet for users to manage funds.
   - Users can add or withdraw money, simplifying fare transactions.

---

### Ride Management

The RideOn app offers dedicated ride functionalities to each user role:

- **Riders**:
  - Request rides by specifying source and destination.
  - Select preferred payment mode (Cash or Wallet).
  - Cancel rides, view ride history, and rate drivers after each ride.
  
- **Drivers**:
  - Receive notifications for nearby ride requests.
  - Accept, start, and end rides, cancel rides if needed.
  - View ride history and rate riders after ride completion.

---

### Driver Matching and Fare Calculation

1. **Driver Matching Strategy**:
   - Implements a strategy design pattern to identify suitable drivers.
   - Matches drivers based on proximity within a 10km radius or highest rating.
   - Notifies matched drivers via email.

2. **Fare Calculation Strategy**:
   - Calculates the fare using either the DefaultFareStrategy or SurgePricingFareStrategy, based on factors like demand and supply.
  
Both strategies ensure optimal pairing and pricing, enhancing the rider's experience with timely service and fair pricing.

---

### Ride Workflow

1. **Requesting a Ride**:
   - Rider submits source, destination, and payment preference.
   - Matching and fare calculation strategies are invoked.

2. **Ride Acceptance**:
   - Driver accepts the ride, and the rider receives a confirmation email with an OTP.
  
3. **Starting the Ride**:
   - Driver reaches the rider’s pickup location, and the rider provides the OTP to verify the ride.
  
4. **Ride Completion**:
   - Upon reaching the destination, the rider completes the payment.
   - Driver ends the ride, and both parties have the option to rate each other.

---

### Payment and Wallet Transactions

1. **Payment Processing**:
   - **Wallet Payment**: Deducted from the rider's wallet; platform fee is subtracted before adding the remainder to the driver’s wallet.
   - **Cash Payment**: Platform fee is deducted directly from the driver’s wallet.

2. **Transaction Management**:
   - Each transaction is logged with appropriate statuses to track and audit payments.

The app supports a clear, efficient transaction flow, simplifying fare payments and facilitating financial management for users.

---

### Rating System

- Riders and Drivers can rate each other after completing a ride.
- Ratings improve the quality of service by helping users make informed decisions.

---

### Enums

The following enums standardize application states and transactions:

1. **RideRequestStatus**:
   - `PENDING`, `CANCELLED`, `CONFIRMED`

2. **RideStatus**:
   - `CANCELLED`, `CONFIRMED`, `ONGOING`, `ENDED`

3. **PaymentStatus**:
   - `PENDING`, `CONFIRMED`, `REFUNDED`

4. **TransactionMethod**:
   - `BANKING`, `RIDE`

5. **TransactionType**:
   - `CREDIT`, `DEBIT`

These enums ensure consistency and accuracy in managing various application states.

---

### Testing and Documentation

1. **Unit Testing**:
   - Comprehensive unit tests cover both successful and failure scenarios for key features like Signup, Login, and OnBoardNewDriver methods.
   
2. **API Documentation**:
   - **Swagger UI** provides an interactive interface for accessing all API endpoints.
   
3. **System Health Monitoring**:
   - Actuators provide insights into the app's performance, facilitating proactive system management.

---

### Conclusion

The RideOn backend is meticulously designed to support efficient ride-booking services with advanced security, transaction management, and user interaction features. With a carefully structured architecture and documentation, RideOn is poised to scale effectively, making it an attractive solution for investors and clients alike.
