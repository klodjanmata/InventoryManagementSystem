package Service;

import Entity.Category;
import Repository.CategoryRepository;
import Util.Helper;
import Util.Printer;

import java.util.List;

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

    public void updateCategory(Category category) {
        categoryRepo.update(category);
    }

    public void deleteCategory(Long id) {
        categoryRepo.delete(id);
    }
}
