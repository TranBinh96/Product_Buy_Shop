package binhtt.controller;

import binhtt.dtos.CategoryDTO;
import binhtt.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(  @RequestParam("page") int page,@RequestParam("limit") int limit){
        return  ResponseEntity.ok(String.format("Page : %d, Limit %d",page,limit));
    }
    @PostMapping("")
    public ResponseEntity<?> insertCategories(@Valid @RequestBody CategoryDTO dto , BindingResult result) {
        if (result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }
        return ResponseEntity.ok("This is insert categories" +dto.getName() );
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
