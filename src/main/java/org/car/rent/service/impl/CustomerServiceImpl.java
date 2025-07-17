package org.car.rent.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.car.rent.exception.CustomerException;
import org.car.rent.model.Customer;
import org.car.rent.repository.CustomerRepository;
import org.car.rent.repository.impl.CustomerRepositoryImpl;
import org.car.rent.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerServiceImpl() {
    this.customerRepository = new CustomerRepositoryImpl();
  }

  @Override
  public Customer addCustomer(Customer customer) throws CustomerException {
    if (StringUtils.isBlank(customer.getFirstName()) || StringUtils.isBlank(customer.getLastName())) {
      throw new CustomerException("The customer information is incorrect. The customer cannot have blank first name and last name.");
    }

    Customer current = customerRepository.getCustomer(customer.getFirstName(), customer.getLastName());
    if (current != null) {
      return current;
    }

    customerRepository.addCustomer(customer);
    return customer;
  }

}
