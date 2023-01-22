package hello.login.web.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filter init!");
    }

    @Override // 필터 로직
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("doFilter log ! ");

        // 다운캐스팅 - ServletRequest 의 자식 interface 가 HttpServletRequest
        HttpServletRequest httpRequest=(HttpServletRequest) request;
        String requestURI=httpRequest.getRequestURI();  // 받은 요청

        String uuid= UUID.randomUUID().toString(); // 랜덤 토큰

        try{
            log.info("요청 로그[{}][{}]", uuid, requestURI);
            chain.doFilter(request,response); // 다음필터 호출 or 다음필터가 없으면 서블릿 호출
        } catch(Exception e){
            throw e;
        } finally{
            log.info("응답 로그[{}][{}]", uuid, requestURI);
        }

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
