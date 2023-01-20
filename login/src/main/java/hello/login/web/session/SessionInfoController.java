package hello.login.web.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;

@Slf4j
@RestController
public class SessionInfoController {

    @GetMapping("/session-info")
    public String sessionInfo(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if(session==null){
            return "세션이 없습니다";
        }
        session.getAttributeNames().asIterator()
                .forEachRemaining(name->log.info("session name={}, value={}",name,session.getAttribute(name))
                );
        log.info("sessionId={} ",session.getId());  // 세션 ID
        log.info("session.getMaxInactiveInterval={}",session.getMaxInactiveInterval()); // 세션 소멸 소요 시간
        log.info("creationTime={}",new Date(session.getCreationTime())); // 세션 만들어진 시간
        log.info("lastAccessedTime={}" ,session.getLastAccessedTime()); // 세션 마지막 접근 시간
        log.info("isNew={}",session.isNew());  // 세션이 기존의 것인지 새것인지

        return "세션 출력";

    }

}
