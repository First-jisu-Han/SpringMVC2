package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.session.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    //private final SessionManager sessionManager;


    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "login/LoginForm";
    }

    //필터의 redirect 로직 까지 품게끔 만듬.
    @PostMapping("/login")
    public String loginV4(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult,
                          @RequestParam(defaultValue = "/") String redirectURL,
                          HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        // null 이 나오거나 Member객체가 나오거나
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디또는 비밀번호가 틀립니다.");
            return "login/loginForm";
        }

        // 로그인 성공 - 세션 보관
        // 세션이 있으면 세션을 반환 , 세션이 없으면 신규세션을 생성한다.
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); // 세션에 보관할 객체,데이터를 보관 (세션이름,담기는객체)

        return "redirect:"+redirectURL;
    }



    /*
    // 서블릿이 제공하는 세션을 사용

    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult,HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }
        // null 이 나오거나 Member객체가 나오거나
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디또는 비밀번호가 틀립니다.");
            return "login/loginForm";
        }

        // 로그인 성공 - 세션 보관
        // 세션이 있으면 세션을 반환 , 세션이 없으면 신규세션을 생성한다.
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember); // 세션에 보관할 객체,데이터를 보관 (세션이름,담기는객체)

        return "redirect:/";
    }
*/
    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request){
        // request의 세션을 반환해서 session 저장. getSession(false) 이기때문에 세션 없어도 세션을 만들지 않음.
        HttpSession session= request.getSession(false);

        //세션있으면 소멸
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }


/*  직접만든 세션 적용한것
    @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute LoginForm form , BindingResult bindingResult,HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        // null 이 나오거나 Member객체가 나오거나
        Member loginMember=loginService.login(form.getLoginId(),form.getPassword());

        if(loginMember==null){
            bindingResult.reject("loginFail","아이디또는 비밀번호가 틀립니다.");
            return "login/loginForm";
        }
        // 로그인 성공 - 세션 생성
        sessionManager.createSession(loginMember,response);
        return "redirect:/";
    }
    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";
    }

 */


    /*
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
     */

//    @PostMapping("/logout")
//    public String logout(HttpServletResponse response){
//        Cookie cookie=new Cookie("memberId",null); // 쿠키이름으로 쿠키 가져오기
//        cookie.setMaxAge(0);   // 쿠키 시간 소멸 -> 쿠키 없애기
//        response.addCookie(cookie); // 쿠키 소멸
//        return "redirect:/";
//    }
    

}


