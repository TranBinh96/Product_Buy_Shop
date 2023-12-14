package binhtt.services.IServices;

import binhtt.dtos.ProductDTO;
import binhtt.dtos.ProductImageDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.exception.InvalidParamException;
import binhtt.models.Product;
import binhtt.models.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO product) throws DataNotFoundException;
    Product updateProduct(long id,ProductDTO product) throws DataNotFoundException;
    Page<Product> getAllProduct(Pageable pageable);

    Product getProductById(long id) throws DataNotFoundException;

    void deleteProductbyId(Long id);

    boolean existsByName(String name);


    ProductImage createProductImage(Long productId,
                                    ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException;
}
