package org.car.rent.repository;

import java.util.List;
import org.car.rent.model.Car;
import org.car.rent.model.CarType;

public interface CarRepository {
  Car getCar(String registrationNumber);
  void addCar(Car car);
  boolean removeCar(String registrationNumber);
  List<Car> getAllCarsByType(CarType type);
}
