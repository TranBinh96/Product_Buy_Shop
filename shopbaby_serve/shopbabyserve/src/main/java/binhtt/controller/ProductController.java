package binhtt.controller;

import binhtt.dtos.ProductDTO;
import binhtt.dtos.ProductImageDTO;
import binhtt.models.Product;
import binhtt.models.ProductImage;
import binhtt.respository.ProductReponsitory;
import binhtt.services.IServices.IProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/product")
public class ProductController {
    private final IProductService productService;
    @GetMapping("")
    public ResponseEntity<String> getAllProduct(@RequestParam("limit") int limit,@RequestParam("page") int page){
        return ResponseEntity.ok(String.format("limit %d, Page : %d",limit,page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getByIdProduct(@PathVariable("id") int productId){
        return  ResponseEntity.ok("Product  id : "+productId);
    }

    @PostMapping(value = "")
    public  ResponseEntity<?> insertPrudct(@Valid @RequestBody ProductDTO dto,
                                           BindingResult result) throws IOException {
        Product newProduct =null;
        try{
            if (result.hasErrors()){
                List<String> errorMessage = result.getFieldErrors().stream().map(FieldError::getDefaultMessage).toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            newProduct = productService.createProduct(dto);

        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }



        return  ResponseEntity.ok(newProduct);

    }

    @PostMapping(value = "/uploads/{id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<?> updateProductImage(@PathVariable("id") long id,@ModelAttribute List<MultipartFile> files) throws IOException {
     String resultMessage ="";
       try{
           Product existsProduct = productService.getProductById(id);
           List<MultipartFile> lstfile = files;
           lstfile = files ==null ? new ArrayList<MultipartFile>() : lstfile;
           for (MultipartFile file:lstfile ) {
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

               ProductImage  productImage = productService.createProductImage(existsProduct.getId(), ProductImageDTO
                       .builder()
                       .imageUrl(storeFile(file)).build());
               resultMessage +=productImage.imageUrl+"\n";
           }
           return  ResponseEntity.ok(resultMessage);

       }catch (Exception exception){
           return  ResponseEntity.badRequest().body(exception.getMessage());

       }


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
