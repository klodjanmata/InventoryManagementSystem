package Service;

import Entity.Customer;
import Entity.Employee;
import Repository.EmployeeRepository;
import Util.Helper;
import Util.Printer;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Employee getEmployee(Long id) {
        return employeeRepo.findById(id);
    }

    public void printAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        Printer.printEmployees(employees);
    }

    public List<Employee> listByRole(String role) {
        return employeeRepo.findByRole(role);
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
            LocalDate newHireDate = Helper.getLocalDateFromUser1("Enter new Hire Date (leave blank to keep current)");

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
}
