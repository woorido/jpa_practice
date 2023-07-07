package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.dto.member.*;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.json.JsonPatch;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result membersV2() {
        List<Member> members = memberService.findMembers();
        List<MemberDto> collect = members.stream()
                .map(m -> new MemberDto(m.getName(), m.getAddress()))
                .collect(Collectors.toList());
        return new Result(collect, collect.size());
    }

    @Data
    @AllArgsConstructor
    class Result<T> {
        private T data;
        private int count;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
        private Address address;
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest memberRequest) {
        Member member = new Member(memberRequest.getName(), memberRequest.getAddress());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberPut(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest memberRequest) {
        memberService.update(id, memberRequest.getName(), memberRequest.getAddress());
        Member member = memberService.findMember(id);
        return new UpdateMemberResponse(member);
    }

    @PatchMapping(value = "/api/v2/members/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<UpdateMemberResponse> updateMemberPatch(@PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch) {
        UpdateMemberResponse updateMemberResponse = memberService.patchMember(id, jsonPatch);
        return ResponseEntity.ok().body(updateMemberResponse);
    }
}
