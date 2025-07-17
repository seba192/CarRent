package org.car.rent.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.car.rent.exception.CustomerException;
import org.car.rent.model.Customer;
import org.car.rent.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CustomerServiceImplTest {

  CustomerService customerService;

  @BeforeEach
  public void setUp() {
    customerService = new CustomerServiceImpl();
  }

  @Test
  public void shouldThrowExceptionWhenFirstNameIsBlank() {
    Customer customer = new Customer("", "lastName");

    Exception exception = assertThrows(CustomerException.class, () -> this.customerService.addCustomer(customer));

    assertEquals("The customer information is incorrect. The customer cannot have blank first name and last name.", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionWhenLastNameIsBlank() {
    Customer customer = new Customer("firstName", "");

    Exception exception = assertThrows(CustomerException.class, () -> this.customerService.addCustomer(customer));

    assertEquals("The customer information is incorrect. The customer cannot have blank first name and last name.", exception.getMessage());
  }

  @Test
  public void shouldAddCorrectCustomer() throws CustomerException {
    Customer customer = new Customer("firstName", "lastName");

    Customer customerDB = this.customerService.addCustomer(customer);

    assertEquals("firstName", customerDB.getFirstName());
    assertEquals("lastName", customerDB.getLastName());
  }

  @Test
  public void shouldReturnExistingCarWhenCarIsInDatabase() throws CustomerException {
    Customer customer = new Customer("firstName", "lastName");
    this.customerService.addCustomer(customer);

    Customer customer2 = new Customer("firstName  ", "  lastName  ");
    Customer customerDB = this.customerService.addCustomer(customer2);

    assertEquals("firstName", customerDB.getFirstName());
    assertEquals("lastName", customerDB.getLastName());
  }

}
