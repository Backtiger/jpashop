package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController //jason이나 xml로 바로 데이터를 전송
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping("api/v1/members")
    public  CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member)//Requestbody json형식으로 온 데이터를 member에 매핑해서 넣음
    {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("api/v2/members")
    public  CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request)//Requestbody json형식으로 온 데이터를 member에 매핑해서 넣음
    {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id,
                                               @RequestBody @Valid UpdateMemberRequest request)
    {
        memberService.update(id,request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }
    @Data
    static class UpdateMemberRequest
    {
        private String name;
    }
    @Data
    @AllArgsConstructor //모든 파라미터를 넘기는 생성자가 필요
    static class UpdateMemberResponse
    {
        private Long id;
        private String name;

    }
    @Data
    static class CreateMemberRequest
    {
        @NotEmpty
          private String name;
    }
    @Data
    public class CreateMemberResponse
    {
        private  Long id;

        public CreateMemberResponse(Long id) {

            this.id=id;
        }
    }
}
