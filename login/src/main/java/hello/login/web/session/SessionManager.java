package hello.login.web.session;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SessionManager {

    public void createSession(Object value, HttpServletResponse response);

    public Object getSession(HttpServletRequest request );

    public Cookie findCookie(HttpServletRequest request, String cookieName);

    public void expire(HttpServletRequest request) ;
}