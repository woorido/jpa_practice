package jpabook.jpashop.dto.member;

import jpabook.jpashop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class GetMemberResponse {
    private int count;
    private List<MemberDto> data;

    public GetMemberResponse(int count, List<MemberDto> data) {
        this.count = count;
        this.data = data;
    }

    @Data
    @AllArgsConstructor
    public static class MemberDto {
        private Long id;
        private String name;
        private Address address;
    }

}
