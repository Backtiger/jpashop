package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name ="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery",fetch = FetchType.LAZY) //Orderclass의 delivery를 참조함
    private Order order;

    @Embedded //Address는 내부 내장타입 있기때문에 임베디드 사용
    private Address address;

    @Enumerated(EnumType.STRING) //enum type ordinal사용하면 숫자로 값이 들어감 0,1,2순
    private DeliveryStatus status;
}
