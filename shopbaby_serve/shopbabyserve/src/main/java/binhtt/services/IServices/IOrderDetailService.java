package binhtt.services.IServices;

import binhtt.dtos.OrderDetailsDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.reponse.OrderDetailReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IOrderDetailService {
    OrderDetailReponse createOrderDetail(OrderDetailsDTO orderDetailsDTO) throws DataNotFoundException;
    OrderDetailReponse getOrderDetaiById(Long orderDetailId) throws DataNotFoundException;
    OrderDetailReponse updateOrderDetail(long orderDetailId, OrderDetailsDTO orderDetailsDTO) throws DataNotFoundException;
    void deleteOrderDetailById(long orderDetailId) throws DataNotFoundException;
    List<OrderDetailReponse> getAllOrderDetailByOrderId(long orderId) throws DataNotFoundException;
    Page<OrderDetailReponse> orderGetAllDetails(PageRequest pageRequest);


}
