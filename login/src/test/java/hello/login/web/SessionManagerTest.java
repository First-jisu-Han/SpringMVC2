package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.web.session.SessionManager;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

public class SessionManagerTest {

    @Autowired
    SessionManager sessionManager;


    @Test
    void sessionTest() {

        // 세션 생성
        MockHttpServletResponse response=new MockHttpServletResponse(); // 각자 테스트 할 수 있게끔 response 가 제공.
        Member member = new Member();
        sessionManager.createSession(member,response);

        // 요청에 대한 응답 쿠키 저장
        MockHttpServletRequest request= new MockHttpServletRequest();
        request.setCookies(response.getCookies()); //  mySessionId= 223430940-2323


        // 세션 조회
        Object result=sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();


    }



}
