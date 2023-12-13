package binhtt.controller;

import binhtt.dtos.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${apiPrefix}/product")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getAllProduct(@RequestParam("limit") int limit,@RequestParam("page") int page){
        return ResponseEntity.ok(String.format("limit %d, Page : %d",limit,page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getByIdProduct(@PathVariable("id") int productId){
        return  ResponseEntity.ok("Product  id : "+productId);
    }

    @PostMapping(value = "",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<?> insertPrudct(@Valid @ModelAttribute ProductDTO dto,
                                           BindingResult result) throws IOException {
        if (result.hasErrors()){
            List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errorMessage);
        }

        List<MultipartFile> files = dto.getFiles();
        files = files ==null ? new ArrayList<MultipartFile>() : files;
        for (MultipartFile file:files ) {
            if (file  !=null){
                if (file.getSize()==0)
                    continue;
                if (file.getSize()>10*1024*1024){
                    return  ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large size 10MB");
                }
                String contenttype = file.getContentType();
                if(contenttype==null || !contenttype.startsWith("image/"))
                    return  ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File must be an image");
            }

            String filename = storeFile(file);
            }



        return  ResponseEntity.ok("insert success");

    }

    private  String storeFile(MultipartFile file ) throws IOException{

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String uniqueFileName = UUID.randomUUID().toString()+"_"+filename;

        Path uploadDir = Paths.get("upload");

        if (!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }

        java.nio.file.Path destination =  Paths.get(uploadDir.toString(),uniqueFileName);
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);

        return  uniqueFileName;
    }

}
