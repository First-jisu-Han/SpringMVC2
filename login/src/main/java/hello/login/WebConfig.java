package hello.login;

import hello.login.web.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;

@Configuration
public class WebConfig {

    // 필터를 스프링 빈으로 등록
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterRegistrationBean=new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter()); // 사용할 필터 등록
        filterRegistrationBean.setOrder(1); // 필터가 체인으로 들어갈 수 있기에 필터 순서정하기
        filterRegistrationBean.addUrlPatterns("/*"); // 어떤 url 패턴 - 이 경우 모든 url에 적용

        return filterRegistrationBean;
    }
}
