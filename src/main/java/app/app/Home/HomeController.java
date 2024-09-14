package app.app.Home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import app.app.user.CustomUserDetails;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        if (user != null) {
            model.addAttribute("username", user.getRealUsername());
        } else {
            model.addAttribute("username", null);
        }
        return "index";  // resources/templates/index.html 반환
    }
}
