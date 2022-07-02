package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Slf4j
public class MemberRepository {

    private static Map<Long,Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(),member);
        return member;
    }
 // null이 올 수 있기 때문에 Optional 로 감싼다.
    public Optional<Member> findByLoginId(String loginId){
        List<Member> all =findAll();
        for(Member member : all){
            if(member.getLoginId().equals(loginId)){
                return Optional.of(member);
            }
        }
            return Optional.empty(); // 아이디가 없는경우 null

    }

    public Member findById(Long id){
        return store.get(id);
    }
    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

}
