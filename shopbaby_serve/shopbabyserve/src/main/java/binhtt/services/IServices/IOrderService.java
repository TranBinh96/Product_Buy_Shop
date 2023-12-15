package binhtt.services.IServices;

import binhtt.dtos.OrderDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.Order;
import binhtt.reponse.OrderReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    OrderReponse createOrder(OrderDTO orderDTO) throws DataNotFoundException;


    List<Order> getOrderByUserId(long userId) throws DataNotFoundException;

    OrderReponse getOrderById(long orderId) throws DataNotFoundException;

    OrderReponse getOrderReponseById(long orderId) throws DataNotFoundException;

    Page<OrderReponse> getAllOrders(Pageable pageable);

    Order updateOrderById(long orderId, OrderDTO orderDTO) throws DataNotFoundException;

    void deleteOrdersById(long orderId) throws DataNotFoundException;


    boolean existsOrderByFullname(String nameFullName);

    boolean existsOrderByEmail(String email);
}
