package binhtt.respository;

import binhtt.models.Order;
import binhtt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderReponsitory extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
}
