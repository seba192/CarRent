package org.car.rent.service;

import org.car.rent.exception.CustomerException;
import org.car.rent.model.Customer;

public interface CustomerService {
  Customer addCustomer(Customer customer) throws CustomerException;
}
