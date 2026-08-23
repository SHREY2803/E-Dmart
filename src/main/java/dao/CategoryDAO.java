package dao;

import java.util.List;

import model.Category;

public interface CategoryDAO {

    // Add new category
    boolean addCategory(Category category);

    // Get all categories
    List<Category> getAllCategories();

    // Get category by ID
    Category getCategoryById(int id);
}