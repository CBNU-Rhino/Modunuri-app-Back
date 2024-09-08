package app.app.post.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    // 게시물 상세 페이지 경로
    @GetMapping("/board/post/{postId}/detail")
    public String getPostDetailPage(@PathVariable Long postId, Model model) {
        // 해당 postId에 맞는 게시물 정보를 가져와서 모델에 추가
        // 예시: PostService로부터 post 정보를 가져옴
        // Post post = postService.getPostById(postId);
        // model.addAttribute("post", post);

        // 예시로, postId만 모델에 추가 (실제 구현에 맞춰서 수정)
        model.addAttribute("postId", postId);

        return "community/post-detail";  // src/main/resources/templates/community/post-detail.html 반환
    }
}
