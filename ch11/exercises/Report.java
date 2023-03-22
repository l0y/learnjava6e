package ch11.exercises;

import java.util.*;

public class Report {
  List<PaidEmployee> employees = new ArrayList<>();

  void buildEmployeeList() {
    employees.add(new PaidEmployee("Fozzi", 4, 30_000));
    employees.add(new PaidEmployee("Gonzo", 2, 50_000));
    employees.add(new PaidEmployee("Kermit", 1, 60_000));
    employees.add(new PaidEmployee("Piggy", 3, 80_000));
  }

  public void publishNames() {
    employees.stream().map(e -> e.getName()).forEach(System.out::println);
  }

  public void publishBudget() {
    int b = employees.stream().mapToInt(e -> e.getSalary()).sum();
    System.out.println("Annual bugdet is " + b);
  }

  public static void main(String args[]) {
    Report r = new Report();
    r.buildEmployeeList();
    r.publishNames();
    r.publishBudget();
  }
}

