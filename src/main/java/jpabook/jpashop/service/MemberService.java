package jpabook.jpashop.service;

import jpabook.jpashop.Repository.MemberRepository;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //데이터 변경 할때는 트랜잭션이 있어야함 transactional사용하면 public메서드는 다 트랜잭션에 걸림 자바스프링제공을 쓸것
//readOnly=true는 jpa가 데이터 조회시 성능 최적화를함 클래스위에 붙이면 기본적으로 read only로 세팅됨
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository; //final을 해주면 값이 없으면 에러 표시를 내줘서 어느부분이 누락되었는지 알 수 있다

   // @Autowired 생성자가 하나만 있을 경우 스프링에서 자동적으로 AutoWired를 해줌
   // 롬복의 @RequiredArgsConstructor 를 사용하면 위의 필드에 final이 붙은 객체만 아래와같은 생성자 인젝션을 자동으로 생성해줌
   // public MemberService(MemberRepository memberRepository) {
   //     this.memberRepository = memberRepository;
   // }


    @Transactional
    //회원가입
    public Long join (Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());//동시에 회원가입할 수 있으므로 unique제약을 걸어두는 것 추천
        //스프링에서 persist하면 값을 알아서 넣어주기때문에 값이 null이면 말이안됨
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미존재하는 회원입니다.");
        }
    }

    //회원전체조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    //멤버아이디를 통한 단건 조회
    public  Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
