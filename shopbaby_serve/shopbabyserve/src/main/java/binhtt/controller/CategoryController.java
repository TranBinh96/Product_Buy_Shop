package binhtt.controller;

import binhtt.dtos.CategoryDTO;
import binhtt.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/categories")
public class CategoryController {

    private  final CategoryService categoryService;

    @PostMapping("")
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
    public ResponseEntity<?> getAllCategories(){
        return  ResponseEntity.ok(categoryService.getAllCategory());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editCategories(@Valid
                                            @PathVariable("id") long id,
                                            @RequestBody CategoryDTO dto){

        categoryService.updateCategoryById(id,dto);
        return  ResponseEntity.ok(String.format("Update Category ID "+dto.getName()+" Success!"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeCategories(@PathVariable Long id){


        return  ResponseEntity.ok(categoryService.deleteCategoryById(id););
    }




}
