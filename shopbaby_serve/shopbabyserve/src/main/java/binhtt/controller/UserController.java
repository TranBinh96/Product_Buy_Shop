package binhtt.controller;

import binhtt.dtos.UserLoginDTO;
import binhtt.dtos.UsersDTO;
import binhtt.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("${apiPrefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsersDTO dto, BindingResult result){
        try{
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            userService.createUser(dto);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return  ResponseEntity.ok(String.format("Insert Success : "+dto.getPhoneNumber()));
    }

    @GetMapping("/login")
    public  ResponseEntity<?>  loginSystem(@Valid @RequestBody UserLoginDTO dto, BindingResult result){
        try{
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }

        return  ResponseEntity.ok(String.format("OK"));
    }
}
