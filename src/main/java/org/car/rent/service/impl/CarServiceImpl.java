package org.car.rent.service.impl;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.car.rent.exception.CarException;
import org.car.rent.model.Car;
import org.car.rent.model.CarType;
import org.car.rent.repository.CarRepository;
import org.car.rent.repository.impl.CarRepositoryImpl;
import org.car.rent.service.CarService;

public class CarServiceImpl implements CarService {

  private final CarRepository carRepository;

  public CarServiceImpl() {
    this.carRepository = new CarRepositoryImpl();
  }

  @Override
  public Car addCar(Car car) throws CarException {
    if (StringUtils.isBlank(car.getName()) || StringUtils.isBlank(car.getRegistrationNumber())) {
      throw new CarException("The car information is incorrect. The car cannot have blank name and registration information.");
    }

    Car currentCar = carRepository.getCar(car.getRegistrationNumber());
    if (currentCar != null) {
      return currentCar;
    }

    carRepository.addCar(car);
    return car;
  }

  @Override
  public boolean removeCar(String registrationNumber) {
    return carRepository.removeCar(registrationNumber);
  }

  @Override
  public List<Car> getCarsByType(CarType type) {
    return carRepository.getAllCarsByType(type);
  }
}
