package org.car.rent.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Car {
  @NonNull
  private String name;
  @NonNull
  private String registrationNumber;
  @NonNull
  private CarType type;
}
