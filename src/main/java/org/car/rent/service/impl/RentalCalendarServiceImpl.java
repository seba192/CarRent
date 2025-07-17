package org.car.rent.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.car.rent.exception.NoCarAvailable;
import org.car.rent.model.Car;
import org.car.rent.model.CarType;
import org.car.rent.model.Customer;
import org.car.rent.model.RentalCalendar;
import org.car.rent.repository.RentalCalendarRepository;
import org.car.rent.service.CarService;
import org.car.rent.service.RentalCalendarService;

public class RentalCalendarServiceImpl implements RentalCalendarService {

  private final RentalCalendarRepository rentalCalendarRepository;

  private final CarService carService;

  public RentalCalendarServiceImpl(RentalCalendarRepository repository, CarService carService) {
    this.rentalCalendarRepository = repository;
    this.carService = carService;
  }

  @Override
  public RentalCalendar rentCar(Customer customer, CarType carType, LocalDateTime start, int numberDays) throws NoCarAvailable {
    List<RentalCalendar> calendar = rentalCalendarRepository.getCalendar(carType, start, numberDays);
    List<Car> rentedCars = calendar.stream()
                                   .map(RentalCalendar::getCar)
                                   .toList();

    Optional<Car> carToRent = carService.getCarsByType(carType)
                                        .stream()
                                        .filter(car -> isCarRented(car, rentedCars))
                                        .findAny();

    if (carToRent.isEmpty()) {
      throw new NoCarAvailable();
    }

    RentalCalendar rentalCalendar = new RentalCalendar(start, numberDays, customer, carToRent.get());
    return rentalCalendarRepository.addRentalCalendar(rentalCalendar);
  }

  @Override
  public boolean cancelRentalCalendar(Customer customer, CarType carType, LocalDateTime start) {
    return rentalCalendarRepository.cancelRentalCalendar(customer, carType, start);
  }

  private boolean isCarRented(Car car, List<Car> rentedCars) {
    return rentedCars.stream()
                     .noneMatch(rented -> rented.getRegistrationNumber()
                                                .equals(car.getRegistrationNumber()));
  }
}
