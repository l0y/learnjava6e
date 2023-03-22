package ch07.solutions;

public class Employee2 implements Comparable<Employee2> {
  private Integer id;
  private String first;
  private String last;

  public Employee2(String firstname, String lastname, Integer id) {
    this.first = firstname;
    this.last = lastname;
    this.id = id;
  }

  public String getFirstName() { return first; }
  public String getLastName() { return last; }
  public Integer getID() { return id; }

  public int compareTo(Employee2 other) {
    // Let's be a little fancy and sort on a constructed name
    String myName = last + ", " + first;
    //String otherName = other.getLastName() + ", " + other.getFirstName();
    String otherName = other.last + ", " + other.first;
    return myName.compareToIgnoreCase(otherName);
  }
}
