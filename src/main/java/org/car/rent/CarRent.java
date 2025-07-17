package org.car.rent;

import java.time.LocalDateTime;
import org.car.rent.exception.CarException;
import org.car.rent.exception.CustomerException;
import org.car.rent.exception.NoCarAvailable;
import org.car.rent.model.Car;
import org.car.rent.model.CarType;
import org.car.rent.model.Customer;
import org.car.rent.repository.RentalCalendarRepository;
import org.car.rent.repository.impl.RentalCalendarRepositoryImpl;
import org.car.rent.service.CarService;
import org.car.rent.service.CustomerService;
import org.car.rent.service.RentalCalendarService;
import org.car.rent.service.impl.CarServiceImpl;
import org.car.rent.service.impl.CustomerServiceImpl;
import org.car.rent.service.impl.RentalCalendarServiceImpl;

public class CarRent {

  public static void main(String[] args) {
    CustomerService customerService = new CustomerServiceImpl();
    CarService carService = new CarServiceImpl();

    RentalCalendarRepository rentalCalendarRepository = new RentalCalendarRepositoryImpl();
    RentalCalendarService rentalCalendarService = new RentalCalendarServiceImpl(rentalCalendarRepository, carService);

    try {
      carService.addCar(new Car("auto1", "KK 12345", CarType.SEDAN));
      carService.addCar(new Car("auto2", "KK 22345", CarType.SEDAN));
      carService.addCar(new Car("auto3", "KK 32345", CarType.SEDAN));
      carService.addCar(new Car("auto4", "KK 42345", CarType.VAN));
      carService.addCar(new Car("auto5", "KK 52345", CarType.SUV));
      carService.addCar(new Car("auto6", "KK 62345", CarType.SUV));
    } catch (CarException e) {
      System.out.println(e.getMessage());
    }

    Customer customer = new Customer("Michał", "Nowak");
    try {
      customerService.addCustomer(customer);
    } catch (CustomerException e) {
      System.out.println(e.getMessage());
    }

    try {
      rentalCalendarService.rentCar(customer, CarType.SEDAN, LocalDateTime.of(2025,7,17, 8, 0), 3);
    } catch (NoCarAvailable e) {
      System.out.println(e.getMessage());
    }
  }
}