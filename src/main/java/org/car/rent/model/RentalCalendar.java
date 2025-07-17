package org.car.rent.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class RentalCalendar {

  @NonNull
  private LocalDateTime startDate;
  @NonNull
  private int numberOfDays;
  @NonNull
  private Customer customer;
  @NonNull
  private Car car;

  public LocalDateTime getEndTime() {
    return startDate.plusDays(numberOfDays - 1);
  }

}
