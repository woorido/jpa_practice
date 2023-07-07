package jpabook.jpashop.dto.member;

import jpabook.jpashop.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMemberResponse {
    private String name;
    private Address address;

    @Data
    @AllArgsConstructor
    class Result<T> {
        private T data;
        private int count;
    }
}
