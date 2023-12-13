package binhtt.services.IServices;

import binhtt.dtos.CategoryDTO;
import binhtt.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(long categoryId);

    List<Category> getAllCategory();

    Category updateCategoryById(long categoryId,CategoryDTO categoryDTO);

    void deleteCategoryById(long categoryId);


}
