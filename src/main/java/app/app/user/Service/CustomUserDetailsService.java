package app.app.user.Service;

import app.app.user.CustomUserDetails;
import app.app.user.Repository.UserRepository;
import app.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 디버그 로그 추가: userId 값을 출력
        System.out.println("로그인 시도 - 입력된 userId: " + userId);

        // userId로 사용자를 찾고, Optional로 감싸서 처리
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUserId(userId));

        // Optional에서 사용자를 가져오거나, 존재하지 않으면 예외를 던짐
        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        // 디버그 로그 추가: 로그인 성공한 사용자 정보 출력
        System.out.println("로그인 성공 - 사용자: " + user.getUsername());

        return new CustomUserDetails(user);
    }
}


