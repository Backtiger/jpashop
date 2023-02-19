package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // inhertinacetype join 정규화때 사용 single_talbe싱글테이블 전략 사용할때 사용 table_per_class 여러 테이블 사용시 사용
@DiscriminatorColumn(name = "dtype")
@Getter @Setter//Setter를 넣지말고 item 클래스안에서 해당 값을 변경해야 한다
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //데이터를 가지고 있는쪽에 비즈니스 로직이 있는 것이 좋다 stockQuantity가 item에 있기때문
    //재고 수량 증가 로직
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity -quantity;
        if (restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
