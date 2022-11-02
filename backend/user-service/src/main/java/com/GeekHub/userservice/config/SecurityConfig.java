package com.GeekHub.userservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .formLogin().disable()
//                .httpBasic().disable()
//                .apply(new MyCustomDsl()) // 커스텀 필터 등록
//                .and()
//                .authorizeRequests(authroize -> authroize.antMatchers("/api/v1/user/**")
//                        .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                        .antMatchers("/api/v1/manager/**")
//                        .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
//                        .antMatchers("/api/v1/admin/**")
//                        .access("hasRole('ROLE_ADMIN')")
//                        .anyRequest().permitAll())
//                .build();
        http
                .httpBasic().disable()   // rest api만을 고려해 기본 설정은 해제
                .csrf().disable()  // csrf 보안 토큰 disable 처리
                .headers()
                .frameOptions()
                .deny()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않음
                // SessionCreationPolicy.STATELESS 설정이 스프링 시큐리티가  무조건 세션을 생성하지 않는다는 의미보다는
                // 인증 처리 관점에서 세션을 생성하지 않음과 동시에 세션을 이용한  방식으로 인증을 처리하지 않겠다는 의미로 해석할 수 있다는 점이고
                // 인증메커니즘이 아닌 다른 특정한 곳에서 세션과 관련된 처리를 해야 하는 경우나
                // 스프링이 아닌 다른 어플리케이션에서 세션을 사용하는 곳이 있다면 세션이 생성 될 수도 있다는 점
                .and()
                .authorizeRequests()  // 요청에 대한 사용 권한 체크
//                .antMatchers("/login", "/signup").permitAll()
                .antMatchers("/api/**").authenticated()
                .anyRequest().permitAll()  // 그 외 나머지 요청은 누구나 접근 가능
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .and()
                .cors();
//                .and();
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}