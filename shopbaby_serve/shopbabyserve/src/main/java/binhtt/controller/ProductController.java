package binhtt.controller;

import binhtt.dtos.ProductDTO;
import binhtt.dtos.ProductImageDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.Product;
import binhtt.models.ProductImage;
import binhtt.reponse.ProductListReponse;
import binhtt.reponse.ProductReponse;
import binhtt.respository.ProductReponsitory;
import binhtt.services.IServices.IProductService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.Objects;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("${apiPrefix}/products")
public class ProductController {
    private final IProductService productService;
    @GetMapping("")
    public ResponseEntity<ProductListReponse> getAllProduct(@RequestParam("page") int page, @RequestParam("limit") int limit){

        PageRequest pageRequest = PageRequest.of(page,limit,
                Sort.by("createAt").descending());
        Page<ProductReponse> productPage = productService.getAllProduct(pageRequest);
        int SumPage = productPage.getTotalPages();
        List<ProductReponse> products =  productPage.getContent();
        return ResponseEntity.ok(ProductListReponse.builder()
                .products(products)
                .totalPage(SumPage)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdProduct(@PathVariable("id") int productId) {
        try{
            return  ResponseEntity.ok(productService.getProductReponsetById(productId));

        }catch (Exception exception){
            return  ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping(value = "/generatefakerproduct")
    private ResponseEntity<String> generateFakerProduct() {
        try{
            Faker faker =new Faker();
            for (int i = 0; i < 8000; i++) {
                String productName = faker.commerce().productName();
                if (productService.existsByName(productName)){
                    continue;
                }
                ProductDTO productDTO =ProductDTO.builder()
                        .name(productName)
                        .price(Float.valueOf(faker.number().numberBetween(0,90_000_00)))
                        .description(faker.lorem().sentence())
                        .thumbnail("")
                        .categoryId(faker.number().numberBetween(1,898))
                        .build();
                productService.createProduct(productDTO);
            }
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Insert Product Faker Success");
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

           if (lstfile.size() >= ProductImage.MAXIMUM_IMAGE_PER_PRODUCT)
               return ResponseEntity.badRequest().body("You can only update maximum 5 images");
           for (MultipartFile file:lstfile ) {
               if (file  !=null){

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

    //delete product by Id

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> deleteProduct(@PathVariable("id") long Id ){
        try{
            productService.deleteProductbyId(Id);
           return ResponseEntity.ok("Delete product by id : "+Id+" Success !!!");

        }catch (Exception exception){
           return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }


    //update image productImage

    private  boolean isImagesFile(MultipartFile file){

        String contactFile = file.getContentType();
        return   contactFile != null && contactFile.startsWith("image/");
    }

    private  String storeFile(MultipartFile file ) throws IOException {
        if (!isImagesFile(file) || file.getOriginalFilename() == null) {
            throw  new IOException("Please check type file format is .jpg || .png");
        }

        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        String uniqueFileName = UUID.randomUUID().toString() + "_" + filename;

        Path uploadDir = Paths.get("upload");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

}
