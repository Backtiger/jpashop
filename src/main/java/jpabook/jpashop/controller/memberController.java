package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class memberController {
    private final MemberService memberService;

    @GetMapping(value = "/members/new") //get방식으로 아래 createMemberForm이 열림
    public String createForm(Model model){
        model.addAttribute("memberForm",new MemberForm()); //memebercontroller에서 view로 넘어갈때 데이터를 같이memberFormhtml에 넘김
        return "members/createMemberForm";
    }
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result)//@Valid를 사용하면 javax.validation에 있는 어노테이션이 memberForm에 있으면 해당 값 예외처리해줌
    {
        if(result.hasErrors()){
            return "members/createMemberForm";
        }
        Address adress = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(adress);

        memberService.join(member);

        return "redirect:/"; //저장 이후의 값은 리다이렉트로 날려버림 첫번째 페이지로 이동됨
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);

        return "members/memberList";
    }
}
