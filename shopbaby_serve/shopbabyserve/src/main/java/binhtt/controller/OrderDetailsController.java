package binhtt.controller;

import binhtt.dtos.OrderDetailsDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/order_details")
public class OrderDetailsController {
    @GetMapping("")
    public ResponseEntity<?> getAllOrderDetails(@Valid @RequestParam("page") int page,@RequestParam("limit") int limit){

        return  ResponseEntity.ok(String.format("Get success  Page: %d, Limit %d",page,limit));
    }
    @GetMapping("/orders/{orderid}")
    public ResponseEntity<?> getByIdOrderDetails(@Valid @PathVariable("orderId") int orderId){

        return ResponseEntity.ok("Get Order_Detail with ID  : "+orderId) ;
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editOrderDetails(@Valid  @PathVariable("id") int id,@RequestBody OrderDetailsDTO dto,BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            return  ResponseEntity.ok(String.format("Edit success id : %d",id));

        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOrderDetails(@Valid  @PathVariable("id") int id){
        try {

            return  ResponseEntity.ok(String.format("Remove success id : %d",id));

        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping()
    public ResponseEntity<?> insertOrderDetails(@Valid @RequestBody OrderDetailsDTO dto, BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            return  ResponseEntity.ok("insert Order Detail Success");

        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
