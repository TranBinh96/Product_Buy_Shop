package binhtt.services;

import binhtt.dtos.CategoryDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.Category;
import binhtt.models.User;
import binhtt.respository.CategoryRespository;
import binhtt.respository.UserReponsitory;
import binhtt.services.IServices.ICategoryService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Builder
public class CategoryService implements ICategoryService {
    private  final CategoryRespository categoryRespository;
    private  final UserReponsitory userReponsitory;

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
    public Page<CategoryDTO> getAllCategory(Pageable pageable) {
        return categoryRespository.findAll(pageable).map(category -> {
            CategoryDTO categoryDTO = CategoryDTO
                    .builder()
                    .name(category.getName())
                    .build();
            return  categoryDTO;
        });
    }

    @Override
    public Category updateCategoryById(long categoryId, CategoryDTO categoryDTO) {
        Category exitsCategor = getCategoryById(categoryId);
        exitsCategor.setName(categoryDTO.getName());
        return categoryRespository.save(exitsCategor);
    }




    @Override
    public void deleteCategoryById(long categoryId) {
        categoryRespository.deleteById(categoryId);
    }

    @Override
    public  boolean existsCategotyByName(String nameCategoty){
        return  categoryRespository.existsByName(nameCategoty);
    }
}
