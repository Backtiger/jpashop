package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable //jpa 내장 타입 컬럼에 들어가는 값 목록 같은 개념
@Getter @Setter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address(){
    }
    //생성자 아무나 만들어 쓸수 없도록 setter는 잘 열어두면안됨
    public Address(String city,String street, String zipcode)
    {
        this.city =city;
        this.street =street;
        this.zipcode = zipcode;

    }
}
