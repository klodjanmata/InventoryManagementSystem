package Service;

import Entity.Employee;
import Repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepo = new EmployeeRepository();

    public void hireEmployee(String name, String role) {
        Employee emp = new Employee();
        emp.setName(name);
        emp.setRole(role);
        emp.setHireDate(LocalDate.now()); // hire date is today by default
        employeeRepo.save(emp);
        System.out.println("✅ Hired employee: " + emp.getName());
    }

    public Employee getEmployee(Long id) {
        return employeeRepo.findById(id);
    }

    public List<Employee> listEmployee() {
        return employeeRepo.findAll();
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
