package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter //실무에서는 getter만 열어두고 @setter는 잘 안열어둔다고함
@Table(name ="orders")
@NoArgsConstructor(access=AccessLevel.PROTECTED)//롬복의 직접 생성자 생성제한 어노테이션 제한의 이유는 필드명이 추가될 경우 메서드를 사용하지않으면 어디서 생성됬는지 찾기 힘들어 추가하기 힘듦
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch  = FetchType.LAZY)  // 하나의 사람이 오더를 여러개 가질 수 있기 때문에 (다대일 관계) many to one 사용
    @JoinColumn(name = "member_id") //어떤 컬럼과 join 할 것인가
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)// orderentity에 여러개의 orderitems가 생성될 수 있다
    //Casecade설정을 all로 했기때문에 order를 persist하면 orderitems에 있는 값들도 persist해줌
    private List<Orderitem> orderitems = new ArrayList<>();

    //1.참조를 한곳에서만 할때 2.persist라이프 사이클이 같을때 사용
    //casecade는 참조를 한곳에서만 할때 사용해야한다 그렇지 않으면 다른곳에서 참조해서 지울때 같이 지워버림
    @OneToOne(fetch  = FetchType.LAZY,cascade = CascadeType.ALL)  //1대1관계일때는 fk를 더 많이 엑세스 (접근)하는곳에 둔다
    @JoinColumn(name = "delivery_id") //연관 관계의 주인 fk가 있는곳에 joinColumn사용
    private Delivery delivery;

    private LocalDateTime orderDate;//자바 8버젼 이상부터는 데이트타임을 알아서 하이버네이트가 매핑해줘서 별도의 어노테이션 지정 필요 없음

    @Enumerated(EnumType.STRING) //enum타입은 STring쓸것 ORDINAL사용하면 숫자로 들어가서 중간에 값이 들어가면 데이터 꼬임
    private OrderStatus status;

    //연관관계 메서드//
    public  void setMember(Member member)
    {
        this.member =member;
        member.getOrders().add(this);
    }

    public void addOrderItem(Orderitem orerItem)
    {
        orderitems.add(orerItem);
        orerItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery)
    {
        this.delivery=delivery;
        delivery.setOrder(this);
    }
    //복잡한 연관관계 생성은 메서드를 사용해서 생성하는것이 좋다 setter대신 메서드 사용
    //생성 메서드
    public static Order createOrder(Member member ,Delivery delivery, Orderitem ... orderItems){
       Order order = new Order();
       order.setMember(member);
       order.setDelivery(delivery);

       for(Orderitem orderitem : orderItems){
           order.addOrderItem(orderitem);
       }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //비즈니스 로직
    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(Orderitem orderItem : orderitems){
            orderItem.cancel();
        }

    }
    //조회로직
    public int getTotalPrice(){
        int totalPrice = 0;
        for(Orderitem orderitem : orderitems){
            totalPrice += orderitem.getToTalPrice();
        }
        return totalPrice;

    }
}
