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


    public void deleteCustomer() {
        Customer customer = new Customer();
        System.out.println("🗑️ Delete Customer");
        printAllCustomers();
        int customerId = Helper.getIntFromUser("Enter Customer ID to delete");

        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepo.findById(customerId));

        if (optionalCustomer.isPresent()) {
            customer = optionalCustomer.get();
            customerRepo.delete(customerId);
            System.out.println("✅ Customer with ID " + customerId + "-" + customer.getName() + " has been deleted.");
        } else {
            System.out.println("❌ No customer found with ID " + customerId);
        }
    }

    public void filterCustomersByName() {
        System.out.println("🔎 Filter Customers by Name");

        // 1. Ask user for search keyword
        String keyword = Helper.getStringFromUser("Enter name or part of name to search");

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("❌ Invalid input. Please enter a valid name.");
            return;
        }

        // 2. Get all customers
        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            System.out.println("⚠️ No customers found in the system.");
            return;
        }

        // 3. Filter by name (case-insensitive, partial match)
        String search = keyword.trim().toLowerCase();
        List<Customer> filtered = customers.stream()
                .filter(c -> c.getName() != null && c.getName().toLowerCase().contains(search))
                .toList();

        // 4. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No customers found with name containing: " + keyword);
        } else {
            System.out.println("✅ Customers matching name '" + keyword + "':");
            Printer.printCustomers(filtered);
        }
    }

    public void filterCustomersByEmailDomain() {
        System.out.println("📧 Filter Customers by Email Domain");

        // 1. Ask user for domain keyword
        String domain = Helper.getStringFromUser("Enter email domain (e.g., gmail.com)");

        if (domain == null || domain.trim().isEmpty()) {
            System.out.println("❌ Invalid input. Please enter a valid domain.");
            return;
        }

        String searchDomain = domain.trim().toLowerCase();

        // 2. Get all customers
        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            System.out.println("⚠️ No customers found in the system.");
            return;
        }

        // 3. Filter by email domain (case-insensitive)
        List<Customer> filtered = customers.stream()
                .filter(c -> c.getEmail() != null && c.getEmail().toLowerCase().endsWith(searchDomain))
                .toList();

        // 4. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No customers found with email domain: " + searchDomain);
        } else {
            System.out.println("✅ Customers with email domain '" + searchDomain + "':");
            Printer.printCustomers(filtered);
        }
    }

    public void filterCustomersByPhonePrefix() {
        System.out.println("📱 Filter Customers by Phone Prefix");

        // 1. Ask user for phone prefix
        String prefix = Helper.getStringFromUser("Enter phone prefix (e.g., +355)");

        if (prefix == null || prefix.trim().isEmpty()) {
            System.out.println("❌ Invalid input. Please enter a valid phone prefix.");
            return;
        }

        String searchPrefix = prefix.trim();

        // 2. Get all customers
        List<Customer> customers = customerRepo.findAll();
        if (customers.isEmpty()) {
            System.out.println("⚠️ No customers found in the system.");
            return;
        }

        // 3. Filter by phone prefix
        List<Customer> filtered = customers.stream()
                .filter(c -> c.getPhone() != null && c.getPhone().startsWith(searchPrefix))
                .toList();

        // 4. Print results
        if (filtered.isEmpty()) {
            System.out.println("⚠️ No customers found with phone prefix: " + searchPrefix);
        } else {
            System.out.println("✅ Customers with phone prefix '" + searchPrefix + "':");
            Printer.printCustomers(filtered);
        }
    }


}

