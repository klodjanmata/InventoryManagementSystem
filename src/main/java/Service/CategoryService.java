package Service;

import Entity.Category;
import Entity.Customer;
import Repository.CategoryRepository;
import Util.Helper;
import Util.Printer;

import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final CategoryRepository categoryRepo = new CategoryRepository();

    public void addCategory() {

        Category category = new Category();
        category.setName(Helper.getStringFromUser("Category Name"));
        if (categoryRepo.findByName(category.getName()) != null) {
            System.out.println("❌ Category with this name already exists!");
            return;
        }
        category.setDescription(Helper.getStringFromUser("Category Description"));
        categoryRepo.save(category);
        System.out.println("✅ Added category: " + category.getName());
    }

    public void printAllCategories(){
        List<Category> categories = categoryRepo.findAll();
        Printer.printCategories(categories);
    }

    public Category getCategory(Long id) {
        return categoryRepo.findById(id);
    }

    public List<Category> listCategories() {
        return categoryRepo.findAll();
    }

    public void updateCategory() {
        Category category = new Category();
        System.out.println("🔄 Update Category Information");
        printAllCategories();
        long categoryId = Helper.getIntFromUser("Enter Customer ID to update");

        Optional<Category> optionalCategory = Optional.ofNullable(categoryRepo.findById(categoryId));

        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();

            System.out.println("Current Info: ");
            System.out.println("Name: " + category.getName());
            System.out.println("Description: " + category.getDescription());
            // Prompt for new values
            String newName = Helper.getStringFromUser("Enter new name (leave blank to keep current)");
            String newDescription = Helper.getStringFromUser("Enter new description (leave blank to keep current)");

            // Update only if new value is provided
            if (!newName.trim().isEmpty()) category.setName(newName);
            if (!newDescription.trim().isEmpty()) category.setDescription(newDescription);
            categoryRepo.update(category);
            System.out.println("✅ Category updated successfully.");
        } else {
            System.out.println("❌ Category with ID " + categoryId + " not found.");
        }
    }

    public void deleteCategory() {
        Category category = new Category();
        System.out.println("🗑️ Delete Category");
        printAllCategories();
        long categoryId = Helper.getIntFromUser("Enter Category ID to delete");

        Optional<Category> optionalCategory = Optional.ofNullable(categoryRepo.findById(categoryId));

        if (optionalCategory.isPresent()) {
            category = optionalCategory.get();
            categoryRepo.delete(categoryId);
            System.out.println("✅ Category with ID " + categoryId + "-" + category.getName() + " has been deleted.");
        } else {
            System.out.println("❌ No customer found with ID " + categoryId);
        }
    }
}
