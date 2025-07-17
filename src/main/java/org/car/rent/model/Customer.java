package org.car.rent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class Customer {

  @NonNull
  private String firstName;
  @NonNull
  private String lastName;
}
