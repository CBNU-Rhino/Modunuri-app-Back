package app.app.post.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardPageController {

    // /community 경로로 요청이 들어오면 board.html 반환
    @GetMapping("/community")
    public String getCommunityPage() {
        return "community/board";  // src/main/resources/templates/community/board.html 파일 반환
    }

    // /board/new 경로로 요청이 들어오면 new-post.html 반환
    @GetMapping("/board/new")
    public String newPostPage() {
        return "community/new-post";  // src/main/resources/templates/community/new-post.html 파일 반환
    }
}
