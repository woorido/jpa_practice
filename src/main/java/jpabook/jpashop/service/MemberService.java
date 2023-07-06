package jpabook.jpashop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr353.JSR353Module;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.dto.member.UpdateMemberResponse;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.json.JsonPatch;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //단건 조회
    @Transactional(readOnly = true)
    public Member findMember(Long id) {
        return memberRepository.findOne(id);
    }

    @Transactional
    public void update(Long id, String name, Address address) {
        Member member = memberRepository.findOne(id);
        member.changeMember(name, address);
    }

    @Transactional
    public UpdateMemberResponse patchMember(Long id, JsonPatch jsonPatch) {
        Member originMember = memberRepository.findOne(id);
        Member modifiedMember = mergePerson(originMember, jsonPatch);
        originMember.changeMember(modifiedMember.getName(), modifiedMember.getAddress());
        return new UpdateMemberResponse(modifiedMember);
    }

    private Member mergePerson(Member originalPerson, JsonPatch jsonPatch) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new JSR353Module());
        JsonStructure target = objectMapper.convertValue(originalPerson, JsonStructure.class);
        JsonValue patchedPerson = jsonPatch.apply(target);
        return objectMapper.convertValue(patchedPerson, Member.class);
    }
}
