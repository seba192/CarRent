package org.car.rent.repository.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.car.rent.model.CarType;
import org.car.rent.model.Customer;
import org.car.rent.model.RentalCalendar;
import org.car.rent.repository.RentalCalendarRepository;

public class RentalCalendarRepositoryImpl implements RentalCalendarRepository {

  List<RentalCalendar> calendar = new ArrayList<>();

  @Override
  public RentalCalendar addRentalCalendar(RentalCalendar rentalCalendar) {
    calendar.add(rentalCalendar);
    return rentalCalendar;
  }

  @Override
  public boolean cancelRentalCalendar(Customer customer, CarType carType, LocalDateTime start) {
    calendar.stream()
            .filter(cal -> cal.getCustomer().getFirstName().equals(customer.getFirstName()) &&
                cal.getCustomer().getLastName().equals(customer.getLastName()))
            .filter(cal -> cal.getCar().getType() == carType)
            .filter(cal -> cal.getStartDate().equals(start))
            .findFirst()
            .ifPresent(cal -> calendar.remove(cal));

    return true;
  }

  @Override
  public List<RentalCalendar> getCalendar(CarType carType, LocalDateTime start, int numberDays) {
    return calendar.stream()
                   .filter(cal -> cal.getCar()
                                     .getType() == carType)
                   .filter(cal -> isInRange(cal.getStartDate(), cal.getEndTime(), start, start.plusDays(numberDays - 1)))
                   .toList();
  }

  private boolean isInRange(LocalDateTime startDate, LocalDateTime endTime,
                            LocalDateTime start, LocalDateTime end
  ) {

    return !(startDate.isBefore(start) && startDate.isAfter(end)) ||
        !(endTime.isBefore(start) && endTime.isAfter(end));
  }
}
