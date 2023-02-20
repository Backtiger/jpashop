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
import java.util.List;
import java.util.stream.Collectors;

@RestController //jason이나 xml로 바로 데이터를 전송
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1()
    {
        return memberService.findMembers();
    }
    @GetMapping("/api/v2/members")
    public Result memberV2()
    {
        List<Member> findmembers= memberService.findMembers();
        List<MemberDto> collect = findmembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(collect.size(),collect);
    }
    @Data
    @AllArgsConstructor //나중에 count라던가 새로운 정보를 요청할수 있으므로 확정성이 유용하게 T로 한번 감싸서 반환함
    static class Result<T>
    {//파라미터로 받아지는 타입들
        private int count;
        private T data;
    }
    @Data
    @AllArgsConstructor
    static class MemberDto{
        private String name;
    }

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
