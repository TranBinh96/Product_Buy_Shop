package binhtt.respository;

import binhtt.models.Order;
import binhtt.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailReponsitory extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findOrderDetailById(Long Id);


}
