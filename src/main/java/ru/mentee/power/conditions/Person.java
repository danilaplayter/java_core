package ru.mentee.power.conditions;

public class Person {

  private int age;
  private boolean licence;

  public Person() {
  }

  public boolean ableToRent() {
    return age >= 18;
  }

  public int getAge() {
    return age;
  }

  public boolean isLicence() {
    return licence;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setLicence(boolean licence) {
    this.licence = licence;
  }
}
