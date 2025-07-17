package org.car.rent.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.car.rent.model.CarType;
import org.car.rent.model.Customer;
import org.car.rent.model.RentalCalendar;

public interface RentalCalendarRepository {
  RentalCalendar addRentalCalendar(RentalCalendar rentalCalendar);
  boolean cancelRentalCalendar(Customer customer, CarType carType, LocalDateTime start);
  List<RentalCalendar> getCalendar(CarType carType, LocalDateTime start, int numberDays);
}
