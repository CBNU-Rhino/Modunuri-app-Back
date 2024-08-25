package app.app.user.Service;


import app.app.user.User;
import app.app.user.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String username, String password, String email) {
        // 이메일 중복 체크
        if (userRepository.findByEmail(email) != null) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(username, encodedPassword, email);
        return userRepository.save(newUser); // 사용자 저장
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            System.out.println("사용자를 찾을 수 없습니다: " + email);
            return null; // 인증 실패
        }

        // 암호 검증을 Spring Security의 PasswordEncoder와 함께 수행
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Spring Security의 Authentication 객체를 생성하고 SecurityContext에 설정
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(), new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("로그인 성공: " + email);
            return user; // 인증 성공
        } else {
            System.out.println("비밀번호 불일치: " + email);
            return null; // 인증 실패
        }
    }


}
