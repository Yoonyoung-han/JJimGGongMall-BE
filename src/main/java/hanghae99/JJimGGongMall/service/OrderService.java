package hanghae99.JJimGGongMall.service;

import hanghae99.JJimGGongMall.domain.*;
import hanghae99.JJimGGongMall.domain.constant.DeliveryStatus;
import hanghae99.JJimGGongMall.domain.constant.OrderStatus;
import hanghae99.JJimGGongMall.domain.constant.PaymentStatus;
import hanghae99.JJimGGongMall.domain.constant.PgPaymentStatus;
import hanghae99.JJimGGongMall.dto.OrderDto;
import hanghae99.JJimGGongMall.dto.OrderItemDto;
import hanghae99.JJimGGongMall.dto.request.RequestOrderDto;
import hanghae99.JJimGGongMall.dto.response.ResponseOrderDetailDto;
import hanghae99.JJimGGongMall.dto.response.ResponseOrderDto;
import hanghae99.JJimGGongMall.repository.interfaces.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OptionCombinationRepository optionCombinationRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public ResponseOrderDto createOrder(Long userId, RequestOrderDto request) {
        BigDecimal totalPrice = new BigDecimal(0);

        Long addressId = request.getDeliveryAddressId();
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : "+ addressId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : "+ userId));

        List<OrderItemDto> orderItems = request.getOrderItems();

        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDto orderItem : orderItems){
            Long productId = orderItem.getProductId();
            Long combinationId = orderItem.getOptionCombinationId();
            Long orderQuantity = orderItem.getQuantity();


            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Not Found : "+ productId));
            ProductOptionCombination combination = optionCombinationRepository.findById(combinationId)
                    .orElseThrow(() -> new EntityNotFoundException("Not Found : "+ combinationId));

            // 재고 줄이기
            product.decrease(orderQuantity);
            combination.decrease(orderQuantity);

            // orderItem
            OrderItem orderItemObj = OrderItem.builder()
                    .price(combination.getPrice())
                    .quantity(orderQuantity)
                    .shippingAddress(address)
                    .product(product)
                    .optionCombination(combination)
                    .deliveryStatus(DeliveryStatus.NOT_READY)
                    .refundCheck(true)
                    .build();

            orderItemList.add(orderItemObj);
            totalPrice = totalPrice.add(combination.getPrice());
        }

        Order order = Order.builder()
                .user(user)
                .discountAmount(BigDecimal.valueOf(0))
                .orderStatus(OrderStatus.CREATED)
                .totalAmount(totalPrice)
                .build();

        order.generateOrderNumber(userId);
        order.addAllOrderDetail(orderItemList);

        orderRepository.save(order);

        // payment 저장
        RequestOrderDto.PaymentInfo paymentInfo = request.getPaymentInfo();

        Payment payment = Payment.builder()
                .amount(totalPrice)
                .order(order)
                .paymentStatus(PaymentStatus.INITIALIZED)
                .cardIssuer(paymentInfo.getCardIssuer())
                .installmentMonths(paymentInfo.getInstallmentMonth())
                .paymentMethod(paymentInfo.getPaymentMethod())
                .paymentDetails(paymentInfo.getPaymentDetails())
                .pgStatus(PgPaymentStatus.REQUESTED)
                .build();

        paymentRepository.save(payment);

        ResponseOrderDto response =  ResponseOrderDto.builder()
                .orderId(order.getId())
                .orderStatus(OrderStatus.CREATED.getDescription())
                .customerId(userId)
                .deliveryAddress(address.getSingleLineAddress())
                .build();

        response.addProductList(orderItemList);

        return response;
    }


    @Transactional
    public List<OrderDto> getAllOrderList(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : " + userId));

        List<OrderDto> result = new ArrayList<>();
        List<Order> orderList = orderRepository.findByUser(user);

        for (Order order : orderList){
            OrderDto orderDto = OrderDto.builder()
                    .orderNumber(order.getOrderNumber())
                    .totalAmount(order.getTotalAmount())
                    .orderStatus(order.getOrderStatus().getDescription())
                    .orderItemList(order.getOrderItemList().stream().map(OrderItemDto::toDto).toList())
                    .build();
            result.add(orderDto);
        }

        return result;
    }

    @Transactional
    public ResponseOrderDetailDto getOrderDetail(Long userId, Long orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Not Found : " + userId));

        Order order = orderRepository.findByUserAndId(user,orderId);

        List<OrderItem> orderItemList = orderItemRepository.findByOrder(order);

        ResponseOrderDetailDto result = ResponseOrderDetailDto.builder()
                .orderDate(order.getCreatedAt())
                .paymentId(order.getPayment().getId())
                .orderStatus(order.getOrderStatus().getDescription())
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .build();

        result.addOrderItems(orderItemList);

        return result;
    }
}
