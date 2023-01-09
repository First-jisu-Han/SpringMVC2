package hello.login.web;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Controller
public class HomeController {

    @Autowired
    public LoginService loginService;
    @Autowired
    public MemberRepository memberRepository;

    @Autowired
    public SessionManager sessionMananger;



    // 쿠키가 없어도 홈화면 접근 되도록 설정 - required=false
//    @GetMapping("/")
//    public String homeLogin(@CookieValue(name="memberId", required = false )Long memberId, Model model){
//
//        if(memberId==null) return "home";
//
//        Member loginMember=memberRepository.findById(memberId);
//        loginMember.getLoginId();
//        if(loginMember==null){ //쿠키가 소멸되어있는 경우가 있을 수 있음
//              return "home";
//        }
//
//        // 로그인 성공 로직
//        model.addAttribute ("member",loginMember);
//        return "loginHome";

    @GetMapping("/")
    public String homeLogin(HttpServletRequest request, Model model){

        // session 관리자에 저장된 회원정보 조회
        Object session=sessionMananger.getSession(request);


        // 로그인
        Member loginMember=memberRepository.findById(memberId);
        loginMember.getLoginId();
        if(loginMember==null){ //쿠키가 소멸되어있는 경우가 있을 수 있음
              return "home";
        }

        // 로그인 성공 로직
        model.addAttribute ("member",loginMember);
        return "loginHome";









    }
}