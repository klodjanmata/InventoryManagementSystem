package Service;

import Entity.Customer;
import Entity.Employee;
import Repository.EmployeeRepository;
import Util.Helper;
import Util.Printer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeService {
    private final EmployeeRepository employeeRepo = new EmployeeRepository();

    public void hireEmployee() {
        Employee employee = new Employee();
        System.out.println("Provide necessary parameters");
        employee.setName(Helper.getStringFromUser("Enter Employee Name"));
        employee.setRole(Helper.getStringFromUser("Enter Employee Role"));
        employee.setHireDate(Helper.getLocalDateFromUser("Enter Hire Date"));
        employeeRepo.save(employee);
        System.out.println("✅ Hired employee: " + employee.getName());
    }

    public void printAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        Printer.printEmployees(employees);
    }

    public void updateEmployee() {
        Employee employee = new Employee();
        System.out.println("🔄 Update Employee Information");
        printAllEmployees();
        long employeeId = Helper.getIntFromUser("Enter Employee ID to update");

        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepo.findById(employeeId));

        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();

            System.out.println("Current Info: ");
            System.out.println("Name: " + employee.getName());
            System.out.println("Role: " + employee.getRole());
            System.out.println("Hire Date: " + employee.getHireDate());

            // Prompt for new values
            String newName = Helper.getStringFromUser("Enter new name (leave blank to keep current)");
            String newRole = Helper.getStringFromUser("Enter new Role (leave blank to keep current)");
            LocalDate newHireDate = Helper.getLocalDateFromUser("Enter new Hire Date (leave blank to keep current)");

            // Update only if new value is provided
            if (!newName.trim().isEmpty()) employee.setName(newName);
            if (!newRole.trim().isEmpty()) employee.setRole(newRole);
            if (newHireDate != null) employee.setHireDate(newHireDate);

            employeeRepo.update(employee);
            System.out.println("✅ Employee updated successfully.");
        } else {
            System.out.println("❌ Employee with ID " + employeeId + " not found.");
        }
    }
    
    public void fireEmployee() {
        Employee employee = new Employee();
        System.out.println("🗑️ Delete Employee");
        printAllEmployees();
        long employeeId = Helper.getIntFromUser("Enter Employee ID to delete");

        Optional<Employee> optionalEmployee = Optional.ofNullable(employeeRepo.findById(employeeId));

        if (optionalEmployee.isPresent()) {
            employee = optionalEmployee.get();
            employeeRepo.delete(employeeId);
            System.out.println("✅ Employee with ID " + employeeId + "-" + employee.getName() + " has been deleted.");
        } else {
            System.out.println("❌ No employee found with ID " + employeeId);
        }
    }

    public void filterEmployeesByRole() {
        System.out.println("🏷️ Filter Employees by Role");

        // 1. Ask user for role keyword
        printAllEmployeeRoles();
        String role = Helper.getStringFromUser("Enter role or part of role to search (e.g., Manager, Cashier)");
        if (role == null || role.trim().isEmpty()) {
            System.out.println("❌ Invalid input. Please enter a valid role.");
            return;
        }

        // 2. Get all employees
        List<Employee> employees = employeeRepo.findAll();
        if (employees.isEmpty()) {
            System.out.println("⚠️ No employees found in the system.");
            return;
        }

        // 3. Filter by role (case-insensitive, partial match)
        String search = role.trim().toLowerCase();
        List<Employee> filtered = employees.stream()
                .filter(e -> e.getRole() != null && e.getRole().toLowerCase().contains(search))
                .toList();

        // 4. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No employees found with role containing: " + role);
        } else {
            System.out.println("✅ Employees with role matching '" + role + "':");
            Printer.printEmployees(filtered);
        }
    }

    public void filterEmployeesByHireDateRange() {
        System.out.println("📅 Filter Employees by Hire Date Range");

        // 1. Ask user for start and end dates using your helper
        LocalDate startDate = Helper.getLocalDateFromUser("Enter start date");
        LocalDate endDate   = Helper.getLocalDateFromUser("Enter end date");

        // 2. Validate input
        if (startDate == null || endDate == null) {
            System.out.println("❌ Both start and end dates are required.");
            return;
        }
        if (endDate.isBefore(startDate)) {
            System.out.println("⚠️ End date cannot be before start date.");
            return;
        }

        // 3. Get all employees
        List<Employee> employees = employeeRepo.findAll();
        if (employees.isEmpty()) {
            System.out.println("⚠️ No employees found in the system.");
            return;
        }

        // 4. Filter employees by hire date range
        List<Employee> filtered = employees.stream()
                .filter(e -> e.getHireDate() != null &&
                        !e.getHireDate().isBefore(startDate) &&
                        !e.getHireDate().isAfter(endDate))
                .toList();

        // 5. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No employees found hired between "
                    + startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    + " and "
                    + endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        } else {
            System.out.println("✅ Employees hired between "
                    + startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    + " and "
                    + endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ":");
            Printer.printEmployees(filtered);
        }
    }

    public void filterEmployeesByName() {
        System.out.println("🔎 Filter Employees by Name");

        // 1. Ask user for search keyword
        String keyword = Helper.getStringFromUser("Enter employee name or part of it");
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("❌ Invalid input. Please enter a valid name.");
            return;
        }

        // 2. Get all employees
        List<Employee> employees = employeeRepo.findAll();
        if (employees.isEmpty()) {
            System.out.println("⚠️ No employees found in the system.");
            return;
        }

        // 3. Filter by name (case-insensitive, partial match)
        String search = keyword.trim().toLowerCase();
        List<Employee> filtered = employees.stream()
                .filter(e -> e.getName() != null && e.getName().toLowerCase().contains(search))
                .toList();

        // 4. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No employees found with name containing: " + keyword);
        } else {
            System.out.println("✅ Employees matching name '" + keyword + "':");
            Printer.printEmployees(filtered);
        }
    }

    public void printAllEmployeeRoles() {
        System.out.println("🏷️ All Employee Roles");

        // 1. Get all employees
        List<Employee> employees = employeeRepo.findAll();
        if (employees.isEmpty()) {
            System.out.println("⚠️ No employees found in the system.");
            return;
        }

        // 2. Extract distinct roles (ignoring null/empty)
        Set<String> roles = employees.stream()
                .map(Employee::getRole)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(r -> !r.isEmpty())
                .map(String::toLowerCase) // normalize case
                .collect(Collectors.toCollection(TreeSet::new)); // sorted & unique

        // 3. Print results
        if (roles.isEmpty()) {
            System.out.println("⚠️ No roles found for employees.");
        } else {
            System.out.println("✅ Roles available in the system:");
            roles.forEach(role -> System.out.println("   • " + Helper.capitalizeFirstLetter(role)));
        }
    }

}
