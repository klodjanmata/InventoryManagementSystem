package Service;

import Entity.Employees;
import Repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;

public class EmployeeService {
    private final EmployeeRepository employeeRepo = new EmployeeRepository();

    public void hireEmployee(String name, String role) {
        Employees emp = new Employees();
        emp.setName(name);
        emp.setRole(role);
        emp.setHireDate(LocalDate.now()); // hire date is today by default
        employeeRepo.save(emp);
        System.out.println("✅ Hired employee: " + emp.getName());
    }

    public Employees getEmployee(Long id) {
        return employeeRepo.findById(id);
    }

    public List<Employees> listEmployees() {
        return employeeRepo.findAll();
    }

    public List<Employees> listByRole(String role) {
        return employeeRepo.findByRole(role);
    }

    public void updateEmployee(Employees emp) {
        employeeRepo.update(emp);
    }

    public void fireEmployee(Long id) {
        employeeRepo.delete(id);
        System.out.println("✅ Employee deleted (if existed).");
    }
}
