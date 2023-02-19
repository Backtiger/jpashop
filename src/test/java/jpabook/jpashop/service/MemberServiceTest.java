package jpabook.jpashop.service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) //junit실행할때 스프링이랑 같이 실행한다고 설정할때 사용
@SpringBootTest //스프링 부트환경에서 테스트할때 사용
@Transactional
//@Rollback(value = false) //transactional은 기본적으로 롤백을 시키기 때문에 rollback을 false로 설정하지않으면 insert쿼리가 안나감
public class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em ;

    @Test
    @Rollback(value = false)
        public void 회원가입() throws Exception
        {
            //given
            Member member = new Member();
            member.setName("kim");
            //when
            Long savedId = memberService.join(member);
            //when
            //em.flush();// 영속성컨테스트에 있는 내용을 db에 저장해줌
            assertEquals(member,memberRepository.findOne(savedId));
        }
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception
    {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
     //   try {
              memberService.join(member2);
       // }
      // catch (IllegalStateException e)
      // {
      //     return;
      // }
        //when
        Assert.fail("예외가발생해야함");

    }
}