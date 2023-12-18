package binhtt.respository;

import binhtt.models.Order;
import binhtt.models.User;
import binhtt.reponse.OrderReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderReponsitory extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
    boolean existsById(long Id);
    boolean existsOrderByEmail(String email);
    boolean existsOrderByFullname(String fullname);
}
