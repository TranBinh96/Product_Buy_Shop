package binhtt.services;

import binhtt.dtos.CategoryDTO;
import binhtt.models.Category;
import binhtt.respository.CategoryRespository;
import binhtt.services.IServices.ICategoryService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Builder
public class CategoryService implements ICategoryService {
    private  final CategoryRespository categoryRespository;

    @Override
    public Category createCategory(CategoryDTO categoryDTO) {
        Category newcategory = Category
                .builder()
                .name(categoryDTO.getName())
                .build();
        categoryRespository.save(newcategory);
        return null;
    }

    @Override
    public Category getCategoryById(long categoryId) {

        return categoryRespository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category Not Found"));
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRespository.findAll();
    }

    @Override
    public Category updateCategoryById(long categoryId, CategoryDTO categoryDTO) {
        Category exitsCategor = getCategoryById(categoryId);
        exitsCategor.setName(categoryDTO.getName());
        return exitsCategor;
    }

    @Override
    public void deleteCategoryById(long categoryId) {
        categoryRespository.deleteById(categoryId);
    }
}
