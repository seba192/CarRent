package org.car.rent.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.car.rent.exception.NoCarAvailable;
import org.car.rent.model.Car;
import org.car.rent.model.CarType;
import org.car.rent.model.Customer;
import org.car.rent.model.RentalCalendar;
import org.car.rent.repository.RentalCalendarRepository;
import org.car.rent.service.CarService;
import org.car.rent.service.RentalCalendarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RentalCalendarServiceImplTest {

  private RentalCalendarService rentalCalendarService;

  @Mock
  RentalCalendarRepository rentalCalendarRepository;
  @Mock
  CarService carService;

  @BeforeEach
  public void setUp() {
    this.rentalCalendarService = new RentalCalendarServiceImpl(rentalCalendarRepository, carService);
  }

  @Test
  public void shouldThrowExceptionWhenNoCarsInDb() {
    Customer customer = new Customer("firstname", "LastName");
    LocalDateTime start = LocalDateTime.of(2025, 7, 17, 8, 0);

    Mockito.when(rentalCalendarRepository.getCalendar(CarType.SEDAN, start, 5))
           .thenReturn(Collections.EMPTY_LIST);
    Mockito.when(carService.getCarsByType(CarType.SEDAN))
           .thenReturn(Collections.EMPTY_LIST);

    assertThrows(NoCarAvailable.class, () -> this.rentalCalendarService.rentCar(customer, CarType.SEDAN, start, 5));
  }

  @Test
  public void shouldThrowExceptionWhenAllCarsAreRented() {
    Customer customer = new Customer("firstname", "LastName");
    LocalDateTime start = LocalDateTime.of(2025, 7, 17, 8, 0);
    Car car = new Car("CarName", "KK 21341", CarType.SEDAN);
    RentalCalendar rentalCalendar = new RentalCalendar(start, 5, customer, car);

    Mockito.when(rentalCalendarRepository.getCalendar(CarType.SEDAN, start, 5))
           .thenReturn(List.of(rentalCalendar));
    Mockito.when(carService.getCarsByType(CarType.SEDAN))
           .thenReturn(List.of(car));

    assertThrows(NoCarAvailable.class, () -> this.rentalCalendarService.rentCar(customer, CarType.SEDAN, start, 5));
  }

  @Test
  public void shouldThrowExceptionWhenCarIsRentedStartDateInRange() {
    Customer customer = new Customer("firstname", "LastName");
    LocalDateTime start = LocalDateTime.of(2025, 7, 17, 8, 0);

    Car car = new Car("CarName", "KK 21341", CarType.SEDAN);
    RentalCalendar rentalCalendar = new RentalCalendar(LocalDateTime.of(2025, 6, 16, 8, 0), 2, customer, car);

    Mockito.when(rentalCalendarRepository.getCalendar(CarType.SEDAN, start, 5))
           .thenReturn(List.of(rentalCalendar));
    Mockito.when(carService.getCarsByType(CarType.SEDAN))
           .thenReturn(List.of(car));

    assertThrows(NoCarAvailable.class, () -> this.rentalCalendarService.rentCar(customer, CarType.SEDAN, start, 5));
  }

  @Test
  public void shouldThrowExceptionWhenCarIsRentedEndDateInRange() {
    Customer customer = new Customer("firstname", "LastName");
    LocalDateTime start = LocalDateTime.of(2025, 7, 17, 8, 0);

    Car car = new Car("CarName", "KK 21341", CarType.SEDAN);
    RentalCalendar rentalCalendar = new RentalCalendar(LocalDateTime.of(2025, 7, 21, 8, 0), 2, customer, car);

    Mockito.when(rentalCalendarRepository.getCalendar(CarType.SEDAN, start, 5))
           .thenReturn(List.of(rentalCalendar));
    Mockito.when(carService.getCarsByType(CarType.SEDAN))
           .thenReturn(List.of(car));

    assertThrows(NoCarAvailable.class, () -> this.rentalCalendarService.rentCar(customer, CarType.SEDAN, start, 5));
  }

  @Test
  public void shouldCarBeReservedIfOneOfCarsIsAvailable() throws NoCarAvailable {
    ArgumentCaptor<RentalCalendar> captor = ArgumentCaptor.forClass(RentalCalendar.class);

    Customer customer = new Customer("firstname", "LastName");
    LocalDateTime start = LocalDateTime.of(2025, 7, 17, 8, 0);

    Car car = new Car("CarName", "KK 21341", CarType.SEDAN);
    Car car2 = new Car("CarName2", "KK 21321", CarType.SEDAN);
    RentalCalendar rentalCalendar = new RentalCalendar(LocalDateTime.of(2025, 7, 17, 8, 0), 2, customer, car);

    Mockito.when(rentalCalendarRepository.getCalendar(CarType.SEDAN, start, 5))
           .thenReturn(List.of(rentalCalendar));
    Mockito.when(carService.getCarsByType(CarType.SEDAN))
           .thenReturn(List.of(car, car2));

    this.rentalCalendarService.rentCar(customer, CarType.SEDAN, start, 5);

    verify(this.rentalCalendarRepository, times(1)).addRentalCalendar(captor.capture());

    assertEquals(5, captor.getValue()
                          .getNumberOfDays());
    assertEquals(start, captor.getValue()
                              .getStartDate());

    assertEquals("firstname", captor.getValue()
                                    .getCustomer()
                                    .getFirstName());
    assertEquals("LastName", captor.getValue()
                                   .getCustomer()
                                   .getLastName());

    assertEquals("CarName2", captor.getValue()
                                   .getCar()
                                   .getName());
    assertEquals(CarType.SEDAN, captor.getValue()
                                      .getCar()
                                      .getType());
    assertEquals("KK 21321", captor.getValue()
                                   .getCar()
                                   .getRegistrationNumber());
  }
}
