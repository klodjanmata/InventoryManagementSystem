package Service;

import Entity.Employee;
import Repository.EmployeeRepository;
import Util.Helper;
import Util.Printer;

import java.time.LocalDate;
import java.util.List;

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

    public void updateEmployee(Employee emp) {
        employeeRepo.update(emp);
    }

    public void fireEmployee(Long id) {
        employeeRepo.delete(id);
        System.out.println("✅ Employee deleted (if existed).");
    }
}
