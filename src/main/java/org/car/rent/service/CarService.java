package org.car.rent.service;

import java.util.List;
import org.car.rent.exception.CarException;
import org.car.rent.model.Car;
import org.car.rent.model.CarType;

public interface CarService {

  Car addCar(Car car) throws CarException;

  boolean removeCar(String registrationNumber);

  List<Car> getCarsByType(CarType type);
}
