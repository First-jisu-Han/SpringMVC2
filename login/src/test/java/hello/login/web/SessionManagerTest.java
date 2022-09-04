package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.web.session.SessionManager;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

public class SessionManagerTest {

    @Autowired
    SessionManager sessionManager;


    @Test
    void sessionTest() {

        // 세션 생성
        HttpServletResponse response=new MockHttpServletResponse(); // 각자 테스트 할 수 있게끔 response 가 제공.
        Member member = new Member();
        sessionManager.createSession(member,response);



    }



}
