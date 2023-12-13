package binhtt.controller;

import binhtt.dtos.OrdersDTO;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/orders")
public class OrderController {

    @GetMapping(value = "")
    public ResponseEntity<?> getAllOrder(@RequestParam("page") int page ,@RequestParam("limit") int limit){

        return  ResponseEntity.ok(String.format("Get Success Order Page %d, Limit %d ",page,limit));
    }

    @PostMapping("")
    public ResponseEntity<?> insertOrder(@Valid @RequestBody OrdersDTO dto ,BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return  ResponseEntity.badRequest().body(errorMessage);
            }
            return  ResponseEntity.ok(String.format("insert success user id  : "+dto.getUserId()));

        }catch (Exception e){
            return
                    ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderByID(@Valid @PathVariable("id") long id ,
                                             @Valid @RequestBody OrdersDTO dto){


        return  ResponseEntity.ok(String.format("Update order order %d sucess",id));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> RemoveOrderByID(@Valid @PathVariable("id") long id ){

        return ResponseEntity.ok(String.format("Remove Order By Id %d  Success",id));
    }


}
