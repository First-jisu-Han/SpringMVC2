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
    public Optional<Member> findByLoginId(String loginId) {
     /*   List<Member> all =findAll();
        for(Member member : all){
            if(member.getLoginId().equals(loginId)){
                return Optional.of(member);
            }
        }
            return Optional.empty(); // 아이디가 없는경우 null

    }  */
        // 람다 식 변형
        return findAll().stream() // 루프 돈다
        .filter(member->member.getLoginId().equals(loginId)) // Member 타입 멤버 찾아서 로그인 아이디 일치하면 그 값반환시키기
                .findFirst(); // 제일 먼저 나오는 데이터 반환
    }
    public Member findById(Long id){
        return store.get(id);
    }
    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

}
