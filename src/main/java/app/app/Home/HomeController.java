package app.app.Home;

import app.app.user.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
        return "index"; // index.html을 렌더링
    }
}