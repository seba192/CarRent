package org.car.rent.repository.impl;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.car.rent.model.Customer;
import org.car.rent.repository.CustomerRepository;

public class CustomerRepositoryImpl implements CustomerRepository {

  private final Map<String, Customer> customers = new HashMap<>();

  @Override
  public Customer addCustomer(Customer customer) {
    return customers.putIfAbsent(getKey(customer.getFirstName(), customer.getLastName()), customer);
  }

  @Override
  public Customer getCustomer(String firstName, String lastName) {
    return customers.get(getKey(firstName, lastName));
  }

  private String getKey(String firstName, String lastName) {
    return StringUtils.deleteWhitespace(firstName.toUpperCase()) + "_" +
        StringUtils.deleteWhitespace(lastName.toUpperCase());
  }
}
