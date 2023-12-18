package binhtt.controller;

import binhtt.dtos.OrderDetailsDTO;
import binhtt.services.OrderDetaiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/order_details")
public class OrderDetailsController {
    private  final OrderDetaiService orderDetaiService;
    @GetMapping("")
    public ResponseEntity<?> getAllOrderDetails(@Valid @RequestParam("page") int page,@RequestParam("limit") int limit){

        return  ResponseEntity.ok(String.format("Get success  Page: %d, Limit %d",page,limit));
    }
    @GetMapping("/order/{orderid}")
    public ResponseEntity<?> getByIdOrderDetails(@Valid @PathVariable("orderId") long orderId){
        try {
            return ResponseEntity.ok(orderDetaiService.getAllOrderDetailByOrderId(orderId));
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage()) ;
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editOrderDetails(@Valid  @PathVariable("id") long id,@RequestBody OrderDetailsDTO dto,BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            return  ResponseEntity.ok(orderDetaiService.updateOrderDetail(id,dto));

        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOrderDetails(@Valid  @PathVariable("id") long id){
        try {
            orderDetaiService.deleteOrderDetailById(id);
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
            return  ResponseEntity.ok(orderDetaiService.createOrderDetail(dto));
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
