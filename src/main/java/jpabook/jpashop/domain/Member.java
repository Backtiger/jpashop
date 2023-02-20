package jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id //id fk설정 해주는 어노테이션
    @GeneratedValue //시퀀스값 같은 고유값 설정 값이 없더라도 스프링이 임의로 값을 생성해서 넣어줌
    @Column(name = "member_id") //컬럼명 설정
    private Long id;

    //@NotEmpty //null값이 안들어가게끔설정
    private  String name;

    @Embedded //address에는 내장 타입의 데이터가 들어간다
    private Address address;

    @JsonIgnore //json호출 무시
    @OneToMany(mappedBy = "member")// 하나의 사람이 여러 order(주문을 하기 때문)에 one to many사용
    //mappedby는 연관관계 fk키를 참조하는 곳에 넣어주고 현재 entity가 member이기 때문에 order에있는 member로 매핑해준다
    private List<Order> orders = new ArrayList<>();

}
