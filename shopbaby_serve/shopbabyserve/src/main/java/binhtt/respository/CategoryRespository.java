package binhtt.respository;

import binhtt.models.Category;
import binhtt.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRespository extends JpaRepository<Category,Long> {
    boolean existsByName(String nameCategory);

    Page<Category> findAll(Pageable pageable); //Phan trang

}
