package binhtt.services;

import binhtt.dtos.ProductDTO;
import binhtt.dtos.ProductImageDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.exception.InvalidParamException;
import binhtt.models.Category;
import binhtt.models.Product;
import binhtt.models.ProductImage;
import binhtt.reponse.ProductReponse;
import binhtt.respository.CategoryRespository;
import binhtt.respository.ProductImageReponsitory;
import binhtt.respository.ProductReponsitory;
import binhtt.services.IServices.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductReponsitory productReponsitory;
    private  final CategoryRespository categoryRespository;
    private  final ProductImageReponsitory productImageReponsitory;

    @Override
    public Product createProduct(ProductDTO productdto) throws DataNotFoundException {
        Category existsCategory =  categoryRespository.findById(productdto.getCategoryId())
                .orElseThrow(()-> new DataNotFoundException("Canot find Category with id  "+productdto.getCategoryId()));
        Product product = Product.builder()
                .name(productdto.getName())
                .price(productdto.getPrice())
                .thumbnail(productdto.getThumbnail())
                .description(productdto.getDescription())
                .category(existsCategory)
                .build();

        return productReponsitory.save(product);
    }

    @Override
    public Product updateProduct(long id, ProductDTO productDTO) throws DataNotFoundException {
        Product product = getProductById(id);
        Category existsCategory = categoryRespository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Found not Category with id  : "+productDTO.getCategoryId()));
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setThumbnail(productDTO.getThumbnail());
        product.setCategory(existsCategory);

        return productReponsitory.save(product);
    }

    @Override
    public Page<ProductReponse> getAllProduct(Pageable pageable) {

        return productReponsitory.findAll(pageable).map(ProductReponse::fromProduct);
    }

    @Override
    public Product getProductById(long id) throws DataNotFoundException {

        return productReponsitory.findById(id).orElseThrow(()-> new DataNotFoundException("Found not product with id : "+id));
    }

    @Override
    public ProductReponse getProductReponsetById(long id) throws DataNotFoundException {

        return productReponsitory.findById(id).map(ProductReponse::fromProduct)
                .orElseThrow(()-> new DataNotFoundException("Found not product with id : "+id));
    }

    @Override
    public void deleteProductbyId(Long id) throws DataNotFoundException {
        Optional<Product> existsProduct = Optional.ofNullable(productReponsitory.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Found't Product Id "+id)));
        existsProduct.ifPresent(productReponsitory::delete);

    }

    @Override
    public boolean existsByName(String name) {
        return productReponsitory.existsByName(name);
    }
    @Override
    public ProductImage createProductImage(long productId,
                                           ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException {
        Product existsProduct = productReponsitory
                .findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Can't found product with id : "+productId));

        ProductImage productImage = ProductImage
                .builder()
                .product(existsProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        int size = productImageReponsitory.findByProductId(productId).size();
        if (size >= ProductImage.MAXIMUM_IMAGE_PER_PRODUCT){
            throw  new InvalidParamException("Number of images must be <= "
                    + ProductImage.MAXIMUM_IMAGE_PER_PRODUCT);
        }
        return productImageReponsitory.save(productImage);

    }
}
