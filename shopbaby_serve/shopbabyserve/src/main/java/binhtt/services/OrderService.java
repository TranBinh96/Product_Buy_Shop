package binhtt.services;

import binhtt.dtos.OrderDTO;
import binhtt.exception.DataNotFoundException;
import binhtt.models.Order;
import binhtt.models.OrderStatus;
import binhtt.models.User;
import binhtt.reponse.OrderReponse;
import binhtt.respository.OrderReponsitory;
import binhtt.respository.UserReponsitory;
import binhtt.services.IServices.IOrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private  final OrderReponsitory orderReponsitory;
    private  final UserReponsitory userReponsitory;
    private  final ModelMapper modelMapper;
    @Override
    public OrderReponse createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        if (!isValidEmail(orderDTO.getEmail())){
            throw  new DataNotFoundException("Email Format Fail");
        }
        User existsUser =  userReponsitory.findById(orderDTO.getUserId()).orElseThrow(() -> new DataNotFoundException(("Found not User with ID : "+orderDTO.getUserId())));

        modelMapper.typeMap(OrderDTO.class,Order.class)
                .addMappings(mapper-> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO,order);
        order.setUser(existsUser);
        order.setStatus(OrderStatus.SHIPPED);
        order.setOrder_date(new Date());
        order.setActive(1);

        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if (shippingDate.isBefore(LocalDate.now()))
            throw new DataNotFoundException("Date must be at last today !");

        order.setShippingDate(shippingDate);
        orderReponsitory.save(order);
        return  modelMapper.map(order,OrderReponse.class);
    }

    @Override
    public List<Order> getOrderByUserId(long userId) throws DataNotFoundException {

        return orderReponsitory.findByUserId(userId);
    }


    private static boolean isValidEmail(String email) {
        // Biểu thức chính quy cho một địa chỉ email hợp lệ
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Tạo Pattern và Matcher
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        // Kiểm tra xem email có khớp với biểu thức chính quy không
        return matcher.matches();
    }

    @Override
    public OrderReponse getOrderById(long orderId) throws DataNotFoundException {

        Order order = orderReponsitory.findById(orderId)
                .orElseThrow(()-> new DataNotFoundException("Found not Order with Id : "+orderId));
        return  modelMapper.map(order,OrderReponse.class);
    }

    @Override
    public OrderReponse getOrderReponseById(long orderId) throws DataNotFoundException {
        return  orderReponsitory.findById(orderId).map(OrderReponse::fromOrder)
                .orElseThrow(()-> new DataNotFoundException("Found not Order with Id : "+orderId));
    }

    @Override
    public Page<OrderReponse> getAllOrders(Pageable pageable) {
        return orderReponsitory.findAll(pageable).map(OrderReponse::fromOrder);
    }

    @Override
    public Order updateOrderById(long orderId, OrderDTO orderDTO) throws DataNotFoundException {
        User existsUser =  userReponsitory.findById(orderDTO.getUserId()).orElseThrow(() -> new DataNotFoundException(("Found not User with ID : "+orderDTO.getUserId())));
        Order order = Order.builder()
                .Id(orderId)
                .user(existsUser)
                .fullname(existsUser.getFullname())
                .email(orderDTO.getEmail())
                .phoneNumber(orderDTO.getPhoneNumber())
                .address(orderDTO.getAddress())
                .note(orderDTO.getNote())
                .totalMoney(orderDTO.getTotalMoney())
                .shippingMethod(orderDTO.getShippingMethod())
                .shippingAddress(orderDTO.getShippingAddress())
                .paymentMethod(orderDTO.getPaymentMethod())
                .build();
        return  orderReponsitory.save(order);
    }

    @Override
    public void deleteOrdersById(long orderId) throws DataNotFoundException {
        Optional<Order> orderOptional = Optional.ofNullable(orderReponsitory
                .findById(orderId).orElseThrow(() -> new DataNotFoundException("Found not Order Id  : " + orderId)));
        orderOptional.ifPresent(orderReponsitory::delete);
    }

    @Override
    public boolean existsOrderByFullname(String nameFullName) {
        return  orderReponsitory.existsOrderByFullname(nameFullName);
    }
    @Override
    public boolean existsOrderByEmail(String email) {
        return  orderReponsitory.existsOrderByEmail(email);
    }
}
