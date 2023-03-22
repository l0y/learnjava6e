package ch11.solutions;

import java.util.*;
import java.util.stream.Collectors;

public class Report2 {
  List<PaidEmployee2> employees = new ArrayList<>();

  void buildEmployeeList() {
    employees.add(new PaidEmployee2("Fozzi", 4, 30_000, "Sales"));
    employees.add(new PaidEmployee2("Gonzo", 2, 50_000, "Developer"));
    employees.add(new PaidEmployee2("Kermit", 1, 60_000, "Manager"));
    employees.add(new PaidEmployee2("Piggy", 3, 80_000, "Manager"));
  }

  public void publishNames() {
    employees.stream().map(e -> e.getName()).forEach(System.out::println);
  }

  public void publishBudget() {
    int b = employees.stream().mapToInt(e -> e.getSalary()).sum();
    System.out.println("Annual bugdet is " + b);
  }

  public void publishAverage() {
    OptionalDouble avg = employees.stream().mapToInt(e -> e.getSalary()).average();
    if (avg.isPresent()) {
      System.out.println("Average salary is " + avg.getAsDouble());
    } else {
      System.out.println("No salaries were available.");
    }
  }

  public void publishStaffByRole() {
    Map<String, List<PaidEmployee2>> byRoles = employees.stream().collect(Collectors.groupingBy(PaidEmployee2::getRole));
    for (String role : byRoles.keySet()) {
      // We could print using a loop as we have in the past:
      System.out.print(role + " (using a loop): ");
      for (PaidEmployee2 emp : byRoles.get(role)) {
        System.out.print(emp.getName() + " ");
      }
      System.out.println();

      // Or we could use yet another collector:
      System.out.print(role + " (using a collector): ");
      System.out.println(byRoles.get(role).stream().map(e -> e.getName()).collect(Collectors.joining(", ")));
    }
  }

  public static void main(String args[]) {
    Report2 r2 = new Report2();
    r2.buildEmployeeList();
    r2.publishNames();
    r2.publishBudget();
    r2.publishAverage();
    r2.publishStaffByRole();
  }
}

