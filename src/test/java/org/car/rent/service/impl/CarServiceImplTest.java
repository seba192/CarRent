package org.car.rent.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.car.rent.exception.CarException;
import org.car.rent.model.Car;
import org.car.rent.model.CarType;
import org.car.rent.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CarServiceImplTest {

  CarService carService;

  @BeforeEach
  public void setUp() {
    carService = new CarServiceImpl();
  }

  @Test
  public void shouldThrowExceptionWhenNameIsBlank() {
    Car car = new Car("", "KK2312", CarType.VAN);

    Exception exception = assertThrows(CarException.class, () -> this.carService.addCar(car));

    assertEquals("The car information is incorrect. The car cannot have blank name and registration information.", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionWhenRegistrationNumberIsBlank() {
    Car car = new Car("Test", " ", CarType.VAN);

    Exception exception = assertThrows(CarException.class, () -> this.carService.addCar(car));

    assertEquals("The car information is incorrect. The car cannot have blank name and registration information.", exception.getMessage());
  }

  @Test
  public void shouldAddCorrectCar() throws CarException {
    Car car = new Car("Test", "KK 29382", CarType.VAN);

    Car savedCar = this.carService.addCar(car);

    assertEquals("Test", savedCar.getName());
    assertEquals("KK 29382", savedCar.getRegistrationNumber());
    assertEquals(CarType.VAN, savedCar.getType());
  }

  @Test
  public void shouldReturnExistingCarWhenCarIsInDatabase() throws CarException {
    Car car = new Car("Test", "KK 29382", CarType.VAN);
    this.carService.addCar(car);

    Car car2 = new Car("Test", "KK29382", CarType.VAN);
    Car savedCar = this.carService.addCar(car2);

    assertEquals("Test", savedCar.getName());
    assertEquals("KK 29382", savedCar.getRegistrationNumber());
    assertEquals(CarType.VAN, savedCar.getType());
  }


  @Test
  public void shouldRemoveCarFromDb() throws CarException {
    Car car = new Car("Test", "KK 29382", CarType.VAN);
    this.carService.addCar(car);

    boolean removed = this.carService.removeCar("KK29382");

    assertTrue(removed);
  }

  @Test
  public void shouldReturnCarsByType() throws CarException {
    this.carService.addCar(new Car("Test", "KK 29382", CarType.VAN));
    this.carService.addCar(new Car("Test2", "KK 39382", CarType.VAN));
    this.carService.addCar(new Car("Test3", "KK 39384", CarType.SEDAN));

    List<Car> carsByType = this.carService.getCarsByType(CarType.SEDAN);

    assertEquals(1, carsByType.size());
    assertEquals("Test3", carsByType.get(0).getName());
    assertEquals("KK 39384", carsByType.get(0).getRegistrationNumber());
    assertEquals(CarType.SEDAN, carsByType.get(0).getType());
  }
}
