package binhtt.services;

import binhtt.dtos.OrderDetailsDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.Order;
import binhtt.models.OrderDetail;
import binhtt.models.Product;
import binhtt.reponse.OrderDetailReponse;
import binhtt.respository.OrderDetailReponsitory;
import binhtt.respository.OrderReponsitory;
import binhtt.respository.ProductReponsitory;
import binhtt.services.IServices.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderDetaiService implements IOrderDetailService {

    private  final OrderReponsitory orderReponsitory;
    private  final OrderDetailReponsitory orderDetailReponsitory;
    private  final ProductReponsitory productReponsitory;
    private ModelMapper modelMapper;

    @Override
    public OrderDetailReponse createOrderDetail(OrderDetailsDTO orderDetailsDTO) throws DataNotFoundException {
        Order order =  orderReponsitory.findById(orderDetailsDTO.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Found not Order with ID  : "+orderDetailsDTO.getOrderId()));
        Product product = productReponsitory.findById(orderDetailsDTO.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Found not Product with ID  : "+orderDetailsDTO.getProductId()));

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .product(product)
                .price(orderDetailsDTO.getPrice())
                .numberOfProducts(orderDetailsDTO.getNumberOfProducts())
                .totalMoney(orderDetailsDTO.getPrice()*orderDetailsDTO.getNumberOfProducts())
                .color(orderDetailsDTO.getColor())
                .build();
        orderDetailReponsitory.save(orderDetail);



        return  OrderDetailReponse.fromOrderDetail(orderDetail);
    }

    @Override
    public OrderDetailReponse getOrderDetaiById(Long orderDetailId) throws DataNotFoundException {
        OrderDetail orderDetail =  orderDetailReponsitory.findById(orderDetailId)
                .orElseThrow(()-> new DataNotFoundException("Found not Order Detail with Id : "+orderDetailId));
        return OrderDetailReponse.fromOrderDetail(orderDetail);
    }

    @Override
    public OrderDetailReponse updateOrderDetail(long orderDetailId, OrderDetailsDTO orderDetailsDTO) throws DataNotFoundException {
        OrderDetail orderDetail = orderDetailReponsitory
                .findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Found not Order Detail with Id  : " + orderDetailId));
        Order order =  orderReponsitory.findById(orderDetailsDTO.getOrderId())
                .orElseThrow(()-> new DataNotFoundException("Found not Order  with Id  : "+orderDetailsDTO.getOrderId()));
        Product product = productReponsitory.findById(orderDetailsDTO.getProductId())
                .orElseThrow(()-> new DataNotFoundException("Found not Product with ID  : "+orderDetailsDTO.getProductId()));

        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setNumberOfProducts(orderDetailsDTO.getNumberOfProducts());
        orderDetail.setPrice(orderDetailsDTO.getPrice());
        orderDetail.setTotalMoney((orderDetailsDTO.getPrice()*orderDetailsDTO.getNumberOfProducts()));
        orderDetail.setColor(orderDetailsDTO.getColor());
        orderDetailReponsitory.save(orderDetail);

        return OrderDetailReponse.fromOrderDetail(orderDetail);
    }

    @Override
    public void deleteOrderDetailById(long orderDetailId) throws DataNotFoundException {

        Optional<OrderDetail> exitOrderDetail = Optional.ofNullable(orderDetailReponsitory
                .findById(orderDetailId)
                .orElseThrow(() -> new DataNotFoundException("Found not Order Detail with ID : "+orderDetailId)));

        exitOrderDetail.ifPresent(orderDetailReponsitory::delete);

    }

    @Override
    public List<OrderDetailReponse> getAllOrderDetailByOrderId(long orderId) throws DataNotFoundException {

        Optional<Order> order = Optional.ofNullable(orderReponsitory.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Found not Order  with Id  : " + orderId)));
        List<OrderDetailReponse> orderDetailsDetailReponses = new ArrayList<>();
        if (order.isPresent()){
            List<OrderDetail> orderDetails = orderDetailReponsitory.findOrderDetailByOrderId(orderId);
            for (OrderDetail orderDetail:orderDetails) {
                orderDetailsDetailReponses.add(OrderDetailReponse.fromOrderDetail(orderDetail));
            }
        }
        return orderDetailsDetailReponses;

    }

    @Override
    public Page<OrderDetailReponse> orderGetAllDetails(PageRequest pageRequest) {
        return orderDetailReponsitory.findAll(pageRequest).map(OrderDetailReponse::fromOrderDetail);
    }
}
