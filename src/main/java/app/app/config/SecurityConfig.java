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
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/css/**", "/script/**", "/images/**", "/static/**").permitAll() // /static/** 또는 /script/** 경로 허용
                        .requestMatchers("/users/signup", "/users/login", "/users/signup_complete").permitAll()
<<<<<<< HEAD
                        .requestMatchers("/touristSpot/**").permitAll() // 이 줄을 추가하여 URL을 허용합니다.
=======
                        .requestMatchers("/users/Search", "/users/Search_seoul", "/users/searchresult").permitAll() // 이 줄을 추가하여 URL을 허용합니다.
                        .requestMatchers("/api/fetch-tourist-data").permitAll() // 이 줄을 추가하여 URL을 허용합니다.
>>>>>>> 8a2e7deb4f032a095336b6f63c4739ccc9e0c48a
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/users/login")
                        .loginProcessingUrl("/users/login")
                        .usernameParameter("userId")  // 여기를 설정
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/users/login?error=true")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/users/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .userDetailsService(customUserDetailsService);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

