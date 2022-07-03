package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final MemberRepository memberRepository;

    // 로그인 제어
    public Member login(String loginId, String password) {
        /*


        Optional<Member> loginMember = memberRepository.findByLoginId(loginId);
        Member member = loginMember.get();
        if (member.getPassword().equals(password)) {
            return member;
        } else {
            return null;
        }


         */

        // optional 스트림 으로 바꾸기 Optional
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);


    }
}
