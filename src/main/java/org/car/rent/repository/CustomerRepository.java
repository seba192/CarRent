package org.car.rent.repository;

import org.car.rent.model.Customer;

public interface CustomerRepository {
  Customer addCustomer(Customer customer);
  Customer getCustomer(String firstName, String lastName);
}
