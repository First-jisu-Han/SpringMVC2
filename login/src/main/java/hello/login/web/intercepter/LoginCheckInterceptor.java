package hello.login.web.intercepter;

import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI=request.getRequestURI();
        log.info("인증체크 인터셉터 Start {}",requestURI);

        HttpSession session=request.getSession();
        // 인증이 안된 경우
        if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            response.sendRedirect("/login?redirectURL="+requestURI);
            return false; // 흐름 stop , 컨트롤러 호출 X
        }




        return true;  // 정상 흐름으로 다음로직으로 이동
    }
}
