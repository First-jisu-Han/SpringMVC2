package hello.login.web.session;


import org.springframework.stereotype.Component;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component  // 스프링 빈으로 등록
public class SessionManagerImpl implements SessionManager {

    public static final String SESSION_COOKIE_NAME="mySessionId";
    private Map<String,Object>  sessionStore=new ConcurrentHashMap<>(); // 동시성 이슈가 있을 수 있음 - 세션 요청이 여러개 들어올 수 있기때문

    /**
     * 세션 생성
     * sessionId 생성 - 추정 불가 랜덤 값
     * session 저장소에 sessionId 와 보관할 값 지정
     * sessionId로 응답 쿠키를 생성해서 클라이언트에 전달
     */
     @Override
     public void createSession(Object value, HttpServletResponse response){
        // session id 생성 , 값을 세션에 저장
        String sessionId=UUID.randomUUID().toString();
        sessionStore.put(sessionId,value);

        Cookie mySessionCookie= new Cookie(SESSION_COOKIE_NAME,sessionId);
        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회
     */
    @Override
    public Object getSession(HttpServletRequest request){
        Cookie sessionCookie=findCookie(request,SESSION_COOKIE_NAME);
        if(sessionCookie==null){
            return null;
        }
        return sessionStore.get(sessionCookie.getValue()); // disdjif222xx 토큰 값으로 나올 것
    }

    // 리펙터링된 코드 - 재활용 용도
    @Override
    public Cookie findCookie(HttpServletRequest request,String cookieName){

        if(request.getCookies()==null) {
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie->cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
    }

     // 세션 매니저 깃헙 감지하게

    /**
     * 세션 만료
     */
    @Override
    public void expire(HttpServletRequest request){
        Cookie sessionCookie = findCookie(request,SESSION_COOKIE_NAME);
        if(sessionCookie!=null){
            sessionStore.remove(sessionCookie.getValue());
        } else {
            System.out.println("세션 쿠키 정상 소멸 X");
        }
    }

}
