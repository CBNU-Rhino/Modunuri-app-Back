package app.app.comment.Service;

import app.app.comment.Comment;
import app.app.comment.Repository.CommentRepository;
import app.app.comment.DTO.CommentRequestDto;
import app.app.post.Post;
import app.app.post.Repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 댓글 생성 로직
    @Transactional
    public Comment createComment(CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(commentRequestDto.getContent());
        comment.setAuthor(commentRequestDto.getAuthor());
        comment.setUserId(commentRequestDto.getUserId());

        return commentRepository.save(comment);
    }

    // 특정 게시물에 대한 댓글 리스트 조회 로직
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByPostId(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));

        return commentRepository.findByPostId(postId);
    }

    // 댓글 삭제 로직
    @Transactional
    public void deleteComment(Long commentId, String userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getUserId().equals(userId)) {
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
