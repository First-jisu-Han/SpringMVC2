package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "login/LoginForm";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult,HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember=loginService.login(form.getLoginId(),form.getPassword());
        if(loginMember==null){
            bindingResult.reject("loginFail","아이디또는 비밀번호가 틀립니다.");
            return "login/loginForm";
        }

        // 로그인 성공 - 세션 쿠키 -> 브라우저를 닫으면 종료 되도록
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);
        return "redirect:/";
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletResponse response){
//        Cookie cookie=new Cookie("memberId",null); // 쿠키이름으로 쿠키 가져오기
//        cookie.setMaxAge(0);   // 쿠키 시간 소멸 -> 쿠키 없애기
//        response.addCookie(cookie); // 쿠키 소멸
//        return "redirect:/";
//    }
    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";
    }

}
