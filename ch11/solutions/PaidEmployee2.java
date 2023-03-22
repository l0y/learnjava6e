package ch11.solutions;

public class PaidEmployee2 {
  private int id;
  private String name;
  private int salary; // annual, in whole dollars
  private String role;

  public PaidEmployee2(String fullname, int id, int salary, String role) {
    this.name = fullname;
    this.id = id;
    this.salary = salary;
    this.role = role;
  }

  public String getName() { return name; }
  public int getID() { return id; }
  public int getSalary() { return salary; }
  public String getRole() { return role; }
}
