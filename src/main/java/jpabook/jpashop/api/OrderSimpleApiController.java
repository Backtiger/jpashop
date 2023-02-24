package jpabook.jpashop.api;

import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.Repository.OrderSearch;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

//xToOne관계 성능최적화
//order
//order-> member 하나의 사람이 주문여러개 manytoOne
//order->delivery  주문하나에 배송한개 one to one
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1()
    {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        //내가 원하는 애만 골라서 출력하는 방법
        for(Order order : all){
            order.getMember().getName();//lazy강제 초기화
            order.getDelivery().getAddress();//lazy강제 초기화
        }
        return all;
    }
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto>ordersV2()
    {
        List<SimpleOrderDto> result = orderRepository.findAllByString(new OrderSearch()).stream()
                .map(o->new SimpleOrderDto(o))
                .collect(Collectors.toList());

        return result;
    }

    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3()
    {
          List<Order> orders= orderRepository.findAllWithMemberDelivery();
          List<SimpleOrderDto> result = orders.stream()
                  .map(o-> new SimpleOrderDto(o))
                  .collect(Collectors.toList());

          return result;
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderDto> orderV4()
    {
        return orderRepository.findOrderDtos();


    }

    @Data //api의 스펙을 규정하는 클래스 DTO
    static class SimpleOrderDto
    {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
        }
    }
}
