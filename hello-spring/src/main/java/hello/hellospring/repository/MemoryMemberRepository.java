package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;


// Repository에서 데이터를 저장한다
@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member); // store에 저장하면 map에 저장됨
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id)); // null이 반환될 가능성이 있을 때 Optional.ofNullable() 사용
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
        // 루프를 돌리면서 이름 같은거 있으면 반환해주기
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // values 가 멤버들
    }

    public void clearStore(){
        store.clear();
    }
}
