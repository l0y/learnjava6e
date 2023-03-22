package ch07.exercises;

public class Employee {
  private Integer id;
  private String first;
  private String last;

  public Employee(String firstname, String lastname, Integer id) {
    this.first = firstname;
    this.last = lastname;
    this.id = id;
  }

  public String getFirstName() { return first; }
  public String getLastName() { return last; }
  public Integer getID() { return id; }
}
