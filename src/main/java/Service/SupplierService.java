package Service;

import Entity.Customer;
import Entity.Supplier;
import Repository.SupplierRepository;
import Util.Helper;
import Util.Printer;

import java.util.List;
import java.util.Optional;

public class SupplierService {

    private final SupplierRepository supplierRepository = new SupplierRepository();

    public void addSupplier() {
        Supplier supplier = new Supplier();
        System.out.println("Provide necessary parameters");
        supplier.setName(Helper.getStringFromUser("Enter Supplier Name"));
        supplier.setContact(Helper.getStringFromUser("Enter Supplier Contact"));
        supplier.setEmail(Helper.getStringFromUser("Enter Supplier Email"));
        supplierRepository.save(supplier);
        System.out.println("✅ Registered supplier: " + supplier.getName());

    }

    public void updateSupplier() {
        Supplier supplier = new Supplier();
        System.out.println("🔄 Update Supplier Information");
        printAllSuppliers();
        int supplierId = Helper.getIntFromUser("Enter Supplier ID to update");

        Optional<Supplier> optionalSupplier = Optional.ofNullable(supplierRepository.findById(supplierId));

        if (optionalSupplier.isPresent()) {
            supplier = optionalSupplier.get();

            System.out.println("Current Info: ");
            System.out.println("Name: " + supplier.getName());
            System.out.println("Contact: " + supplier.getContact());
            System.out.println("Email: " + supplier.getEmail());

            // Prompt for new values
            String newName = Helper.getStringFromUser("Enter new name (leave blank to keep current)");
            String newContact = Helper.getStringFromUser("Enter new contact (leave blank to keep current)");
            String newEmail = Helper.getStringFromUser("Enter new email (leave blank to keep current)");

            // Update only if new value is provided
            if (!newName.trim().isEmpty()) supplier.setName(newName);
            if (!newContact.trim().isEmpty()) supplier.setContact(newContact);
            if (!newEmail.trim().isEmpty()) supplier.setEmail(newEmail);

            supplierRepository.update(supplier);
            System.out.println("✅ Supplier updated successfully.");
        } else {
            System.out.println("❌ Supplier with ID " + supplierId + " not found.");
        }
    }

    public void deleteSupplier() {
        Supplier supplier = new Supplier();
        System.out.println("🗑️ Delete Supplier");
        printAllSuppliers();
        int supplierId = Helper.getIntFromUser("Enter Supplier ID to delete");

        Optional<Supplier> optionalSupplier = Optional.ofNullable(supplierRepository.findById(supplierId));

        if (optionalSupplier.isPresent()) {
            supplier = optionalSupplier.get();
            supplierRepository.delete(supplier);
            System.out.println("✅ Customer with ID " + supplierId + "-" + supplier.getName() + " has been deleted.");
        } else {
            System.out.println("❌ No customer found with ID " + supplierId);
        }
    }

    public Supplier getSupplierById(int id) {
        return supplierRepository.findById(id);
    }

    public void printAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        Printer.printSuppliers(suppliers);

    }
}
