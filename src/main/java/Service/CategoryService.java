package Service;

import Entity.Category;
import Repository.CategoryRepository;

import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepo = new CategoryRepository();

    public void addCategory(String name, String description) {
        if (categoryRepo.findByName(name) != null) {
            throw new RuntimeException("❌ Category with this name already exists!");
        }
        Category category = new Category(null, name, description);
        categoryRepo.save(category);
        System.out.println("✅ Added category: " + category.getName());
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
