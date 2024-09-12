package app.app.comment.Repository;

import app.app.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글(postId)에 해당하는 댓글 리스트 조회
    List<Comment> findByPostId(Long postId);
}
