package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name ="category_id")
    private Long id;

    private String name ;

    @ManyToMany
    @JoinTable(name ="categroy_item",// 관계형 db는 다대다관계를 할수 없기때문에 1대다 다대1로 만들어주는 중간 테이블이 꼭 필요함
               joinColumns = @JoinColumn(name = "category_id"), //카테고리id로 조인
               inverseJoinColumns = @JoinColumn(name = "item_id") //중간테이블의 item값 추가
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch  = FetchType.LAZY)  //부모객체니까 부모하나의 여러개의 자식이 있음
    @JoinColumn(name = "parent_id")
    private Category parent; //계층구조는 부모와 자식 노드로 구성되어있음

    @OneToMany(mappedBy = "parent") //자식객체니까 위의 부모를 참조함
    private List<Category> child = new ArrayList<>();

    //연관관계편의 메서드 양방향 설정 시에 사용
    public void addChildCategory(Category child)
    {
        this.child.add(child);
        child.setParent(this);
    }
}
