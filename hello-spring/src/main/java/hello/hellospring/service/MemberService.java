package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


//Service에서 비즈니스 로직을 만든다
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired // MemberService를 생성할 때 @Service인 것을 확인하고 스프링 컨테이너에 등록하며 생성자를 호출함->Autowired를 보면
                //memberRepository가 필요하구나 -> 스프링 컨테이너의 MemoryMemberRepository를 서비스에 주입해줌
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member member){
        // 같은 이름이 있는 중복 회원 X

        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getID();
    }

    private void validateDuplicateMember(Member member){
        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> { // 멤버의 값이 있으면
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    // 전제회원조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
