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
        System.out.println("loadUserByUsername called with userId: " + userId);

        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUserId(userId));
        User user = userOptional.orElseThrow(() ->
                new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        System.out.println("User found: " + user.getUserId());

        return new CustomUserDetails(user);
    }

}
