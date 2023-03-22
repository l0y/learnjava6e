package ch07.solutions;

import java.util.*;

public class EmployeeList2 {
  public static void main(String args[]) {
    Map<Integer,Employee2> employees = new HashMap<>();
    employees.put(2, new Employee2("Plato", "Athens", 2));
    employees.put(4, new Employee2("Alcibiades", "Athens", 4));
    employees.put(27, new Employee2("Crito", "Alopece", 27));
    employees.put(64, new Employee2("Phaedo", "Elis", 64));
    employees.put(125, new Employee2("Apollodorus", "Phaleron", 125));

    List<Employee2> es = new ArrayList<>(employees.values());
    Collections.sort(es);

    for (Employee2 e : es) {
      System.out.print("Employee #" + e.getID() + " : ");
      System.out.println(e.getFirstName() + " " + e.getLastName());
    }
  }
}
