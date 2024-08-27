package app.app.user.Controller;

import app.app.user.DTO.UserDTO;
import app.app.user.User;
import app.app.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class userController {

    @Autowired
    private UserService userService;

    // 회원가입 페이지를 반환하는 GET 메서드
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userDTO", new UserDTO()); // 폼에서 사용할 빈 객체를 모델에 추가
        return "users/signup"; // templates/users/signup.html 파일을 반환
    }

    // 회원가입 요청을 처리하는 POST 메서드
    @PostMapping("/signup")
    public String register(@ModelAttribute UserDTO userDTO, RedirectAttributes redirectAttributes) {
        try {
            // UserDTO를 사용하여 사용자 등록
            User newUser = userService.registerUser(
                    userDTO.getUsername(),
                    userDTO.getPassword(),
                    userDTO.getEmail());
            redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다!");
            return "redirect:/users/signup_complete"; // 회원가입 완료 페이지로 리다이렉트
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/users/signup"; // 오류 시 다시 회원가입 페이지로 리다이렉트
        }
    }

    @GetMapping("/signup_complete")
    public String signupComplete() {
        return "users/signup_complete"; // resources/templates/signup_complete.html을 렌더링
    }

    // 로그인 폼을 표시하는 GET 메서드
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserDTO()); // UserDTO 객체를 생성하여 모델에 추가
        return "users/login"; // 로그인 템플릿 반환
    }

    // My Page를 표시하는 GET 메서드
    @GetMapping("/mypage")
    public String showMyPage(Model model) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // 이메일을 사용하여 사용자 식별

        // 사용자의 관심 관광지 목록 가져오기
        List<String> favoriteContents = userService.getFavoriteContents(email);

        // 모델에 사용자 이름과 관심 관광지 목록 추가
        model.addAttribute("username", email);
        model.addAttribute("favoriteContents", favoriteContents);
        return "users/mypage"; // My Page 템플릿 반환
    }

    // 관심 관광지 추가를 처리하는 POST 메서드
    @PostMapping("/addFavorite")
    public String addFavorite(@RequestParam("contentId") String contentId, RedirectAttributes redirectAttributes) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try {
            userService.addFavoriteContent(email, contentId);
            redirectAttributes.addFlashAttribute("message", "관심 관광지가 추가되었습니다!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/users/mypage"; // My Page로 리다이렉트
    }

    // 관심 관광지 제거를 처리하는 POST 메서드
    @PostMapping("/removeFavorite")
    public String removeFavorite(@RequestParam("contentId") String contentId, RedirectAttributes redirectAttributes) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        try {
            userService.removeFavoriteContent(email, contentId);
            redirectAttributes.addFlashAttribute("message", "관심 관광지가 삭제되었습니다!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/users/mypage"; // My Page로 리다이렉트
    }
}
