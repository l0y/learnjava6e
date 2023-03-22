package ch11.exercises;

public class PaidEmployee {
  private int id;
  private String name;
  private int salary; // annual, in whole dollars

  public PaidEmployee(String fullname, int id, int salary) {
    this.name = fullname;
    this.id = id;
    this.salary = salary;
  }

  public String getName() { return name; }
  public int getID() { return id; }
  public int getSalary() { return salary; }
}
