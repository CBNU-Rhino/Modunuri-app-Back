package app.app.post.Controller;

import app.app.post.Post;
import app.app.post.Service.PostService;
import app.app.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 모든 게시물 가져오기
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    // ID로 특정 게시물 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 게시물 생성하기
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        // 현재 로그인한 사용자의 ID를 가져와서 author 필드에 설정
        String currentUsername = getCurrentUsername();
        post.setAuthor(currentUsername);

        String currentUserId = getCurrentUserid();
        post.setUserId(currentUserId);

        // 게시물 생성일 자동 설정
        post.setCreatedAt(LocalDateTime.now());

        return postService.createPost(post);
    }

    // 게시물 수정하기
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
        try {
            // 게시물 수정 시 수정 날짜를 현재 시각으로 갱신
            postDetails.setUpdatedAt(LocalDateTime.now());
            Post updatedPost = postService.updatePost(id, postDetails);
            return ResponseEntity.ok(updatedPost);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 게시물 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // 현재 로그인한 사용자의 이름을 가져오는 유틸리티 메서드
    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // CustomUserDetails가 principal인 경우 실제 username을 반환
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getRealUsername(); // 실제 username 반환
        } else if (principal instanceof UserDetails) {
            // 일반적인 UserDetails인 경우 기본 username 반환 (이 경우 userId일 가능성이 있음)
            return ((UserDetails) principal).getUsername();
        } else {
            // 그 외의 경우 principal의 toString() 값을 반환
            return principal.toString();
        }
    }
    // 현재 로그인한 사용자의 id을 가져오는 유틸리티 메서드
    private String getCurrentUserid() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // CustomUserDetails가 principal인 경우 실제 username을 반환
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUsername(); // 실제 username 반환
        } else if (principal instanceof UserDetails) {
            // 일반적인 UserDetails인 경우 기본 username 반환 (이 경우 userId일 가능성이 있음)
            return ((UserDetails) principal).getUsername();
        } else {
            // 그 외의 경우 principal의 toString() 값을 반환
            return principal.toString();
        }
    }

}
