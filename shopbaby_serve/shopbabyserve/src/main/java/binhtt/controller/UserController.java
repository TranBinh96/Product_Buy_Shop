package binhtt.controller;

import binhtt.dtos.UsersDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UsersDTO dto, BindingResult result)throws IOException{
        try{
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());

        }


        return  ResponseEntity.ok(String.format("Insert OK"));
    }

    @GetMapping("/login")
    public  ResponseEntity<?>  loginSystem(@Valid @RequestBody UsersDTO users, BindingResult result){
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
