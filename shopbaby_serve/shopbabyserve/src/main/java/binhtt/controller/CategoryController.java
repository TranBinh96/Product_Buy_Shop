package binhtt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @GetMapping("")
    public ResponseEntity<String> getAllCategories(){
        return  ResponseEntity.ok("Chào Bạn ha ha ");
    }
    @PostMapping("")
    public ResponseEntity<String> insertCategories(){
        return ResponseEntity.ok("This is insert categories");
    }
    @PutMapping("")
    public ResponseEntity<String> editCategories(){
        return  ResponseEntity.ok("Edit complete");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCategories(@PathVariable Long id){
        return  ResponseEntity.ok("Remove complete"+id);
    }


}
