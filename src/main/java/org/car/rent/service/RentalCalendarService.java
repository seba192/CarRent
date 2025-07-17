package org.car.rent.service;

import java.time.LocalDateTime;
import org.car.rent.exception.NoCarAvailable;
import org.car.rent.model.CarType;
import org.car.rent.model.Customer;
import org.car.rent.model.RentalCalendar;

public interface RentalCalendarService {

  RentalCalendar rentCar(Customer customer, CarType carType, LocalDateTime start, int numberDays) throws NoCarAvailable;

  boolean cancelRentalCalendar(Customer customer, CarType carType, LocalDateTime start);
}
