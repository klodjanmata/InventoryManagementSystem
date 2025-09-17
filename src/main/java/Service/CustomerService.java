package Service;

import Entity.Customer;
import Repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepo = new CustomerRepository();

    public void registerCustomer(String name, String email, String phone) {
        if (customerRepo.findByEmail(email) != null) {
            throw new RuntimeException("❌ Customer with this email already exists!");
        }
        Customer customer = new Customer(0, name, email, phone); // id auto-generated
        customerRepo.save(customer);
        System.out.println("✅ Registered customer: " + customer.getName());
    }

    public Customer getCustomer(int id) {
        return customerRepo.findById(id);
    }

    public List<Customer> listCustomers() {
        return customerRepo.findAll();
    }

    public void updateCustomer(Customer customer) {
        customerRepo.update(customer);
    }

    public void deleteCustomer(int id) {
        customerRepo.delete(id);
    }
}

