package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;

//엔티티에 화면에 전달하는 메서드랑 로직을 추가하면 나중에 비대해져서 관리하기 힘들기때문에 form객체나 dto를 따로만들어 사용
@Getter @Setter
public class MemberForm {

    @NotEmpty(message ="회원이름은 필수 입니다.")//null값이 안들어가게끔 필수값 지정해주는 어노테이션
    private String name;

    private String city;
    private String street;
    private String zipcode;

}
