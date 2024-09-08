package app.app.post.Controller;

import app.app.post.Post;
import app.app.post.Service.PostService;
import app.app.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 모든 게시물 가져오기
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
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
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        // 현재 로그인한 사용자의 ID와 이름을 가져와서 author와 userId 필드에 설정
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            post.setAuthor(userDetails.getRealUsername()); // 실제 username 설정
            post.setUserId(userDetails.getUsername()); // ID 설정
        }

        // 게시물 생성일 자동 설정
        post.setCreatedAt(LocalDateTime.now());

        Post createdPost = postService.createPost(post);
        return ResponseEntity.ok(createdPost);
    }
    //게시물 수정하기
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails, Authentication authentication) {
        // 인증된 사용자로부터 CustomUserDetails 가져오기
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            // 사용자의 실제 username (realUsername) 또는 원하는 필드를 설정
            String currentUsername = userDetails.getRealUsername(); // CustomUserDetails에서 실제 사용자 이름 추출

            // 기존 게시물 정보 불러오기
            Post existingPost = postService.getPostById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + id));

            // postDetails에서 제목과 내용을 기존 게시물에 적용
            existingPost.setTitle(postDetails.getTitle());
            existingPost.setContent(postDetails.getContent());
            existingPost.setUpdatedAt(LocalDateTime.now());

            // author를 자동으로 설정 (로그인된 사용자 이름으로 설정)
            existingPost.setAuthor(currentUsername);

            // 게시물 업데이트 및 저장
            Post updatedPost = postService.save(existingPost);

            // 업데이트된 게시물을 응답으로 반환
            return ResponseEntity.ok(updatedPost);
        } else {
            // 인증 정보가 없거나 올바르지 않으면 오류 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    // 게시물 삭제하기
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

}
