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
    public ResponseEntity<?> ceateCategories(@Valid @RequestBody CategoryDTO dto , BindingResult result) {
        if (result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        categoryService.createCategory(dto);
        return ResponseEntity.ok("This is insert categories" +dto.getName() );
    }

    @GetMapping("")
    public ResponseEntity<String> getAllCategories(  @RequestParam("page") int page,@RequestParam("limit") int limit){
        return  ResponseEntity.ok(String.format("Page : %d, Limit %d",page,limit));
    }

    @PutMapping("")
    public ResponseEntity<?> editCategories(){

        return  ResponseEntity.ok("Edit complete");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeCategories(@PathVariable Long id){

        return  ResponseEntity.ok("Remove complete"+id);
    }




}
