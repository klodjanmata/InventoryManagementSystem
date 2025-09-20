package Service;

import Entity.Customer;
import Repository.CustomerRepository;
import Util.Helper;
import Util.Printer;

import java.util.List;

public class CustomerService {
    private final CustomerRepository customerRepo = new CustomerRepository();

    public void registerCustomer() {
        Customer customer = new Customer();
        System.out.println("Provide neccessary parameters");
        customer.setName(Helper.getStringFromUser("Enter Customer Name"));
        customer.setPhone(Helper.getStringFromUser("Enter Customer Phone"));
        customer.setEmail(Helper.getStringFromUser("Enter Customer Email"));
        customerRepo.save(customer);
        System.out.println("✅ Registered customer: " + customer.getName());
    }

    public void printAllCustomers() {
        List<Customer> customers = customerRepo.findAll();
        Printer.printCustomers(customers);
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

