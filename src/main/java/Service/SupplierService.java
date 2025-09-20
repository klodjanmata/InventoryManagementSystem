package Service;

import Entity.Supplier;
import Repository.SupplierRepository;
import Util.Helper;
import Util.Printer;

import java.util.List;

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

    public void updateSupplier(Supplier supplier) {
        supplierRepository.update(supplier);
    }

    public void deleteSupplier(Supplier supplier) {
        supplierRepository.delete(supplier);
    }

    public Supplier getSupplierById(int id) {
        return supplierRepository.findById(id);
    }

    public void printAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        Printer.printSuppliers(suppliers);

    }
}
