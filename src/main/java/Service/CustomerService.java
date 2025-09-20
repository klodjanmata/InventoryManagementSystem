package Service;

import Entity.Customer;
import Repository.CustomerRepository;
import Util.Helper;
import Util.Printer;
import jakarta.persistence.Entity;

import java.util.List;
import java.util.Optional;

public class CustomerService {
    private final CustomerRepository customerRepo = new CustomerRepository();

    public void registerCustomer() {
        Customer customer = new Customer();
        System.out.println("Provide necessary parameters");
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

    public void updateCustomer() {
        Customer customer = new Customer();
        System.out.println("🔄 Update Customer Information");
        printAllCustomers();
        int customerId = Helper.getIntFromUser("Enter Customer ID to update");

        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepo.findById(customerId));

        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();

            System.out.println("Current Info: ");
            System.out.println("Name: " + customer.getName());
            System.out.println("Phone: " + customer.getPhone());
            System.out.println("Email: " + customer.getEmail());

            // Prompt for new values
            String newName = Helper.getStringFromUser("Enter new name (leave blank to keep current)");
            String newPhone = Helper.getStringFromUser("Enter new phone (leave blank to keep current)");
            String newEmail = Helper.getStringFromUser("Enter new email (leave blank to keep current)");

            // Update only if new value is provided
            if (!newName.trim().isEmpty()) customer.setName(newName);
            if (!newPhone.trim().isEmpty()) customer.setPhone(newPhone);
            if (!newEmail.trim().isEmpty()) customer.setEmail(newEmail);

            customerRepo.update(customer);
            System.out.println("✅ Customer updated successfully.");
        } else {
            System.out.println("❌ Customer with ID " + customerId + " not found.");
        }
    }



    public Customer getCustomer(int id) {
        return customerRepo.findById(id);
    }


    public void deleteCustomer(int id) {
        customerRepo.delete(id);
    }
}

