package jpabook.jpashop.Repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository//컴포넌트 스캔에 의해서 자동으로 빈으로 등록됨
@RequiredArgsConstructor
public class MemberRepository {



    // @PersistenceContext 스프링이 enititymanager를 만들어서 em에 의존관계주입해줌
    // 스프링 데이터 jpa 사용시 @PersistenceContext를 @Autowried로 변경 가능
    private final EntityManager em;

   //롬복의 @RequiredArgsConstructor를 사용해서 아래 생성자 인젝션을 자동생성
   // public MemberRepository(EntityManager em) {
   //     this.em = em;
   // }

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id){
      return em.find(Member.class,id);
    }

    public List<Member> findAll(){
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
    return result;
    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name",Member.class)
                .setParameter("name",name) //파라미터 값 입력
                .getResultList(); //리턴형식이 list이기때문에 형변환
    }
}
