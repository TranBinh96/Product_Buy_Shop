package binhtt.controller;

import binhtt.dtos.OrderDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.Order;
import binhtt.reponse.OrderListReponse;
import binhtt.reponse.OrderReponse;
import binhtt.services.OrderService;
import com.github.javafaker.Faker;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/orders")
@RequiredArgsConstructor
public class OrderController {
    private  final OrderService orderService;
    private  final ModelMapper modelMapper;

    @GetMapping(value = "")
    public ResponseEntity<?> getAllOrder(@RequestParam("page") int page ,@RequestParam("limit") int limit){

        PageRequest pageRequest =PageRequest.of(page,limit,
                Sort.by("CreateAt").descending());
        Page<OrderReponse> orderReponsePage = orderService.getAllOrders(pageRequest);
        List<OrderReponse> orderReponses = orderReponsePage.getContent();
        int sumPage  = orderReponsePage.getTotalPages();
        return  ResponseEntity.ok(OrderListReponse.builder()
                        .orders(orderReponses)
                        .totalPage(sumPage)
                        .build());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") int Id ){
        try{
            return  ResponseEntity.ok(orderService.getOrderById(Id));
        }catch (Exception exception){
            return  ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping(value = "/user/{id}")
    public  ResponseEntity<?> getOrderByUserId(@PathVariable("id") long userId){
        try {
            List<OrderReponse> orderReponses = new ArrayList<>();
            List<Order> orders = orderService.getOrderByUserId(userId);
            for (Order order:orders ) {
                orderReponses.add(modelMapper.map(order, OrderReponse.class));
            }
            return  ResponseEntity.ok(orderReponses);
        }catch (Exception exception){
            return  ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
    @PostMapping("")
    public ResponseEntity<?> insertOrder(@Valid @RequestBody OrderDTO dto , BindingResult result){
        try {
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return  ResponseEntity.badRequest().body(errorMessage);
            }
            return  ResponseEntity.ok(orderService.createOrder(dto));

        }catch (Exception e){
            return
                    ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderByID(@Valid @PathVariable("id") long id ,
                                             @Valid @RequestBody OrderDTO dto){
        try{
           Order order =  orderService.updateOrderById(id,dto);
           return  ResponseEntity.ok(modelMapper.map(order,OrderDTO.class));
        }catch (Exception  exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> RemoveOrderByID(@Valid @PathVariable("id") long id ) throws DataNotFoundException {
        orderService.deleteOrdersById(id);
        return ResponseEntity.ok(String.format("Remove Order By Id %d  Success",id));
    }

    @PostMapping(value = "generatefakerorder")
    public  ResponseEntity<?> generateFakerOrder() throws DataNotFoundException {
        Faker faker = new Faker();
        for (int i = 0; i < 100000; i++) {
            String fullname = faker.name().fullName();
            String email =  faker.internet().emailAddress();
            if (orderService.existsOrderByFullname(fullname) && orderService.existsOrderByEmail(email))
                continue;

            OrderDTO orderDTO = OrderDTO.builder()
                    .userId((long) faker.number().numberBetween(1,1_000))
                    .fullname(fullname)
                    .totalMoney((float) faker.number().numberBetween(1_000_000,6_000_000))
                    .shippingAddress(String.valueOf(faker.address()))
                    .phoneNumber(String.valueOf(faker.phoneNumber()))
                    .note( faker.commerce().promotionCode())
                    .email(email)
                    .build();
            orderService.createOrder(orderDTO);
        }
        return  ResponseEntity.ok("Generat Order Success !!! ");
    }
}
