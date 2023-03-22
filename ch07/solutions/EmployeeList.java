package ch07.solutions;

import java.util.*;

public class EmployeeList {
  public static void main(String args[]) {
    Map<Integer,Employee> employees = new HashMap<>();
    employees.put(2, new Employee("Plato", "Athens", 2));
    employees.put(4, new Employee("Alcibiades", "Athens", 4));
    employees.put(27, new Employee("Crito", "Alopece", 27));
    employees.put(64, new Employee("Phaedo", "Elis", 64));
    employees.put(125, new Employee("Apollodorus", "Phaleron", 125));

    List<Integer> ids = new ArrayList<>(employees.keySet());
    Collections.sort(ids);

    for (Integer id : ids) {
      Employee e = employees.get(id);
      System.out.print("Employee #" + id + " : ");
      System.out.println(e.getFirstName() + " " + e.getLastName());
    }
  }
}
