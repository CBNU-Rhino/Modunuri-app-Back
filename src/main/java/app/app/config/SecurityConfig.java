package app.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()  // 정적 리소스에 대한 접근 허용
                        .requestMatchers("/", "/users/signup", "/users/login").permitAll()  // 비로그인 사용자도 접근 가능
                        .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
                )
                .formLogin((form) -> form
                        .loginPage("/users/login") // 커스텀 로그인 페이지 설정
                        .defaultSuccessUrl("/", true) // 로그인 성공 시 이동할 페이지
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/users/logout") // 로그아웃 URL 설정
                        .logoutSuccessUrl("/users/login") // 로그아웃 성공 후 이동할 페이지
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // JSESSIONID 쿠키 삭제
                        .permitAll()
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션이 필요할 때만 생성
                        .maximumSessions(1) // 사용자당 최대 세션 수 제한
                        .expiredUrl("/users/login") // 세션 만료 시 이동할 페이지
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화 방식 설정
    }
}
