package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "login/LoginForm";
    }
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember=loginService.login(form.getLoginId(),form.getPassword());
        if(loginMember==null){
            bindingResult.reject("loginFail","아이디또는 비밀번호가 틀립니다.");
            return "login/loginForm";
        }
        // 로그인 성공 , 쿠키 부여
        Cookie IdCookie=new Cookie("memberId",String.valueOf(loginMember.getLoginId()));
        response.addCookie(IdCookie); // 서블릿 응답에 쿠키를 담기 - 세션쿠키: 웹브라우저 종료시 소멸, 웹브라우저 지속해서 Cookie를 보내줌
        return "redirect:/";
    }
}
