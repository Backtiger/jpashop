package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name ="order_item")
@NoArgsConstructor(access = AccessLevel.PROTECTED)//롬복의 생성자 제한 어노테이션
public class Orderitem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch  = FetchType.LAZY) //주문은 하나지만 item은 여러개이기 때문에 manytoone
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne (fetch  = FetchType.LAZY) //주문하나에 여러 내용을 가질 수 있음
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;//주문가격

    private int count; //주문수량

    //생성메서드를 통해서만 생성자를 만들기로 했으니 protected를 사용해 다른곳에서 직접 생성하는것을 막을 수 있다
    //이는 롬복의 @NoArgsConstructor(access = AccessLevel.PROTECTED)와 같다
 //  protected Orderitem() {
 //  }

    //생성메서드
    public static Orderitem createOrderItem(Item item , int orderPrice ,int count){
        Orderitem orderItem = new Orderitem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
    //비즈니스 로직
    public void cancel() {
        getItem().addStock(count);
    }
    //조회로직 전체가격 조회
    public int getToTalPrice() {
        return getOrderPrice() * getCount(); //주문가격 * 주문수량 총가격
    }
}
