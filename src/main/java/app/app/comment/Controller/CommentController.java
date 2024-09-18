package app.app.comment.Controller;

import app.app.comment.Comment;
import app.app.comment.Service.CommentService;
import app.app.comment.DTO.CommentRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 작성 API
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        System.out.println("Received comment: " + commentRequestDto);
        Comment comment = commentService.createComment(commentRequestDto);
        return ResponseEntity.ok(comment);
    }

    // 특정 게시글의 댓글 리스트 조회 API
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    // 댓글 삭제 API
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @RequestParam String userId) {
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok().build();
    }
}
