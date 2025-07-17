package org.car.rent.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.car.rent.model.Car;
import org.car.rent.model.CarType;
import org.car.rent.repository.CarRepository;

public class CarRepositoryImpl implements CarRepository {

  private final Map<String, Car> cars = new HashMap<>();

  @Override
  public Car getCar(String registrationNumber) {
    return cars.get(StringUtils.deleteWhitespace(registrationNumber));
  }

  @Override
  public void addCar(Car car) {
    cars.put(StringUtils.deleteWhitespace(car.getRegistrationNumber()), car);
  }

  @Override
  public boolean removeCar(String registrationNumber) {
    cars.remove(StringUtils.deleteWhitespace(registrationNumber));
    return true;
  }

  @Override
  public List<Car> getAllCarsByType(CarType type) {
    return cars.values().stream()
        .filter(car -> car.getType() == type)
        .toList();
  }
}
