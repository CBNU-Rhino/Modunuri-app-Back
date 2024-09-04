package app.app.post.Controller;

import app.app.post.Post;
import app.app.post.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardPageController {

    @Autowired
    private PostService postService;  // PostService 인스턴스를 주입받음

    // /community 경로로 요청이 들어오면 board.html 반환
    @GetMapping("/community")
    public String getCommunityPage(Model model) {
        // 현재 로그인한 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = authentication.getName(); // 로그인한 사용자의 ID 가져오기

        // 모델에 currentUserId 추가
        model.addAttribute("currentUserId", currentUserId);

        return "community/board";  // src/main/resources/templates/community/board.html 파일 반환
    }

    // /board/new 경로로 요청이 들어오면 new-post.html 반환
    @GetMapping("/board/new")
    public String newPostPage() {
        return "community/new-post";  // src/main/resources/templates/community/new-post.html 파일 반환
    }

    // 게시물 상세 페이지 경로
    @GetMapping("/board/post/{postId}/detail")
    public String getPostDetailPage(@PathVariable Long postId, Model model) {
        // PostService로부터 post 정보를 가져옴 (인스턴스를 통해 호출)
        Post post = postService.getPostById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));

        // 모델에 post 객체 추가
        model.addAttribute("post", post);

        return "community/post-detail";  // src/main/resources/templates/community/post-detail.html 반환
    }

}
