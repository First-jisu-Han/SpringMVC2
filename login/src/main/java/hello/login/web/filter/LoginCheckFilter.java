package hello.login.web.filter;

import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList= {"/","/member/add","/login","/logout","/css/*"};  // 인증체크 피할 부분

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest =  (HttpServletRequest) request;
        String requestURI=httpRequest.getRequestURI();  // 요청 URL

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 start={}",requestURI); // 요청 url 확인

            if(isLoginCheckPage(requestURI)){
                log.info("<----인증체크 로직---->{}",requestURI);
                HttpSession session = httpRequest.getSession(false); // session 리턴
                // session 이 없거나 session 이 일치하지 않는경우
                if(session==null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
                    log.info("미인증 사용자 요청 {}",requestURI);
                    // 로그인page 리다이렉트
                    httpResponse.sendRedirect("/login?redirectURL="+requestURI); // 로그인 성공후 작업하던 페이지로 보내기
                    return;
                }
            }
            chain.doFilter(request,response);

        } catch(Exception e) {
            throw e;
        } finally{
            log.info("<-----------인증체크 로직 종료 {}------->",requestURI);
        }
    }

    // 로그인 체크할 URL 인지 아닌지 판별
    public boolean isLoginCheckPage(String requestURL){
        return !PatternMatchUtils.simpleMatch(whiteList,requestURL); // URL 매칭여부 확인 boolean타입
    }


}
