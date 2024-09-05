package app.app.post.Controller;

import app.app.post.Post;
import app.app.post.Service.PostService;
import app.app.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

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
    @GetMapping("/board/post/{postId}/detail")
    public String getPostDetail(@PathVariable Long postId, Model model) {
        // 현재 인증된 사용자를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증 정보가 올바르게 설정되어 있는지 로그로 확인
        if (authentication == null) {
            System.out.println("Authentication is null.");
        } else {
            System.out.println("Authentication: " + authentication);
        }

        // 인증된 사용자가 존재하고, 인증 주체가 CustomUserDetails인 경우
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails userDetails) {
            String currentUserId = userDetails.getUsername(); // 현재 사용자 ID
            model.addAttribute("currentUserId", currentUserId);  // 현재 사용자 ID를 모델에 추가
            System.out.println("Current User ID: " + currentUserId);
        } else {
            System.out.println("Authentication principal is not an instance of CustomUserDetails.");
            model.addAttribute("currentUserId", "not access"); // 인증되지 않은 경우
        }

        // postId로 게시물 데이터를 불러옴
        Optional<Post> postOptional = postService.getPostById(postId); // postId로 게시물 조회
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            model.addAttribute("post", post);
            model.addAttribute("postUserId", post.getUserId()); // 게시물 작성자 ID를 모델에 추가
            System.out.println("Post User ID: " + post.getUserId());
        } else {
            System.out.println("Post not found with ID: " + postId);
            return "error"; // 게시물을 찾을 수 없는 경우 오류 페이지로 이동
        }

        // 모델 값 확인용 로그 추가
        System.out.println("Model Attributes: currentUserId=" + model.getAttribute("currentUserId") +
                ", postUserId=" + model.getAttribute("postUserId"));

        return "community/post-detail";  // 템플릿 경로에 맞춰 반환
    }

//    // 게시물 상세 페이지 경로
//    @GetMapping("/board/post/{postId}/detail")
//    public String getPostDetailPage(@PathVariable Long postId, Model model) {
//        // PostService로부터 post 정보를 가져옴 (인스턴스를 통해 호출)
//        Post post = postService.getPostById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + postId));
//
//        // 모델에 post 객체 추가
//        model.addAttribute("post", post);
//
//        return "community/post-detail";  // src/main/resources/templates/community/post-detail.html 반환
//    }

}
