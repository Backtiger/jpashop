package jpabook.jpashop.service;

import jpabook.jpashop.Repository.ItemRepository;
import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.Repository.OrderRepository;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.Repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository; //주문정보
    private final MemberRepository memberRepository; //회원정보
    private final ItemRepository itemRepository; //상품정보
    //주문
    //아래와같이 서비스 계층이 createorder같은 비즈니스 로직을 위임만하고 entity계층에서 비즈니스로직을 가지고있는것이 도메인 모델패턴이라함 jpa에서 주로사용
    //반대로 비즈니스로직이 서비스계층에있고 entity계층은 비즈니스로직이 없는것을 트랜잭션 스크립트패턴이라고함 sql직접 사용할때 사용
    //아래처럼 핵심 로직의 경우에는 entity를 받아서 쓰지말고 해당 로직안에서 찾아서 쓰는것이 더 좋다 왜냐면 밖에서 가지고오면 트랜젝션이 안걸린상태로 들어오기때무에 영속성이 관계없다 판단해서 더티체킹등의 기능이 안될수 있음
    @Transactional
    public long order(Long memberId, Long itemId, int count){

        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
        //주무 상품 생성
        Orderitem orderItem = Orderitem.createOrderItem(item,item.getPrice(),count);
        //주문
        Order order =Order.createOrder(member,delivery,orderItem);
        //저장
        orderRepository.save(order); //원래대로라면 delivery도 따로저장하고 orderitem도 따로 저장해주어야하난 cascade설정때문에 order만 저장해도 문제없다

        return order.getId();
    }
    //취소
    @Transactional
    public void cancelOrder(Long orderid){
        //주문 엔티티조회
        Order order = orderRepository.findOne(orderid);
        //취소 jpa를 사용하지 않고 sql을 직접 사용하면 데이터 변경시 update쿼리를 다시 작업해야 하지만 jpa는 entity에 변경된 데이터가 있으면 자동적용시켜 update해줌
        order.cancel();
    }
    //검색
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}
