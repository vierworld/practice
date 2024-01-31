package ru.vw.practice.lesson2;

public class Employee {
  private String name;
  private int age;
  private String job;

  public Employee(String name,
                  int age,
                  String job) {
    this.age = age;
    this.job = job;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getJob() {
    return job;
  }

  public void setJob(String job) {
    this.job = job;
  }

  @Override
  public String toString() {
    return String.format("{name: %s, age: %s, job: %s}",name,age,job);
  }

  public static EmployeeBuilder builder() {
    return new EmployeeBuilder();
  }

  public static class EmployeeBuilder {
    private String name;
    private int age;
    private String job;

    public EmployeeBuilder name(String name) {
      this.name = name;

      return this;
    }

    public EmployeeBuilder age(int age) {
      this.age = age;
      return this;
    }

    public EmployeeBuilder job(String job) {
      this.job = job;
      return this;
    }

    public Employee build() {
      return new Employee(name, age, job);
    }

  }

}
