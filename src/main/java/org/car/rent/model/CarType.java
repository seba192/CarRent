package org.car.rent.model;

public enum CarType {
  SEDAN("Sedan"), SUV("SUV"), VAN("VAN");

  private String type;

  CarType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }
}
