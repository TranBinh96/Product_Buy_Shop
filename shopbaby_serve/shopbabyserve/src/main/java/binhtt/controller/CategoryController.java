package binhtt.controller;

import binhtt.dtos.CategoryDTO;
import binhtt.models.Category;
import binhtt.reponse.CategoryListReponse;
import binhtt.services.CategoryService;
import binhtt.services.IServices.ICategoryService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/categories")
public class CategoryController {

    private  final ICategoryService categoryService;

    @PostMapping(value = "",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCategories(@Valid @RequestBody CategoryDTO dto , BindingResult result) {
        if (result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        categoryService.createCategory(dto);
        return ResponseEntity.ok("This is insert categories" +dto.getName() );
    }

   /* @GetMapping("")
    public ResponseEntity<String> getAllCategories(  @RequestParam("page") int page,@RequestParam("limit") int limit){
        return  ResponseEntity.ok(String.format("Page : %d, Limit %d",page,limit));
    }
*/

    @GetMapping("")
    public ResponseEntity<?> getAllCategories(@RequestParam("page")int page,@RequestParam("limit") int limit){
        PageRequest pageRequest = PageRequest.of(page,limit,
                Sort.by("Id").descending());

        Page<CategoryDTO> categoryPage = categoryService.getAllCategory(pageRequest);
        List<CategoryDTO> categoryDTOS = categoryPage.getContent();

        int sumPage = categoryPage.getTotalPages();

        return  ResponseEntity.ok(CategoryListReponse.builder().categorys(categoryDTOS).totalPage(sumPage).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCategories(@Valid
                                            @PathVariable("id") long id,
                                            @RequestBody CategoryDTO dto){

        categoryService.updateCategoryById(id,dto);
        return  ResponseEntity.ok(String.format("Update Category ID "+dto.getName()+" Success!"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeCategories(@PathVariable long id,@RequestBody CategoryDTO categoryDTO){
        categoryService.updateCategoryById(id,categoryDTO);
        return  ResponseEntity.ok("Remove Category "+id+" Success");
    }

   /* @PostMapping(value = "generatefakercategory")*/
    public  ResponseEntity<?> generateFakerCategory(){
        Faker faker  = new Faker();
        for (int i=0; i<1000;i++){
         String nameCategory = faker.company().name();
         boolean checkname = categoryService.existsCategotyByName(nameCategory);
         if (checkname){
             continue;
         }
         CategoryDTO categoryDTO = CategoryDTO
                 .builder()
                 .name(nameCategory)
                 .build();
         categoryService.createCategory(categoryDTO);

        }
        return  ResponseEntity.ok("Generate Faker Category Success !!!");

    }



}
