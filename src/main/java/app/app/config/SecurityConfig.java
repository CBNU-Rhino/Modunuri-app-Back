package app.app.config;

import app.app.user.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)  // HTTP 기본 인증 비활성화
                .formLogin((form) -> form
                        .loginPage("/users/login")  // 로그인 페이지
                        .loginProcessingUrl("/users/login")  // 로그인 처리 URL
                        .usernameParameter("userId")  // 사용자 ID 파라미터
                        .passwordParameter("password")  // 비밀번호 파라미터
                        .defaultSuccessUrl("/", true)  // 로그인 성공 시 리디렉션할 URL
                        .failureUrl("/users/login?error=true")  // 로그인 실패 시 리디렉션할 URL
                        .permitAll()  // 로그인 페이지는 모든 사용자에게 허용
                )
                .logout((logout) -> logout
                        .logoutUrl("/users/logout")  // 로그아웃 URL
                        .logoutSuccessUrl("/users/login?logout")  // 로그아웃 성공 후 리디렉션할 URL
                        .invalidateHttpSession(true)  // 세션 무효화
                        .deleteCookies("JSESSIONID")  // 쿠키 삭제
                        .permitAll()  // 로그아웃은 모든 사용자에게 허용
                )
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/**", "/script/**", "/images/**", "/static/**").permitAll()  // 정적 리소스 허용
                        .requestMatchers("/users/signup", "/users/login", "/users/signup_complete").permitAll()  // 회원가입 및 로그인 관련 URL 허용
                        .requestMatchers("/touristSpot/**").permitAll()  // 특정 경로 허용
                        .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
                )
                .userDetailsService(customUserDetailsService);  // 사용자 인증 정보 설정

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호 암호화
    }
}
