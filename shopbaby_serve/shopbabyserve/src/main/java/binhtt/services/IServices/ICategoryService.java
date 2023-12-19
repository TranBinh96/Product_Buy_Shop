package binhtt.services.IServices;

import binhtt.dtos.CategoryDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(long categoryId);

    Page<CategoryDTO> getAllCategory(Pageable pageable);

    Category updateCategoryById(long categoryId,CategoryDTO categoryDTO);

    void deleteCategoryById(long categoryId);


    boolean existsCategotyByName(String nameCategoty);
}
