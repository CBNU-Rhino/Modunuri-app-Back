package app.app.course;

import app.app.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;  // 코스 이름

    @ElementCollection
    @CollectionTable(name = "course_content_ids", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "content_id")
    private List<String> contentIds = new ArrayList<>();  // 여러 관광지의 contentId 리스트로 저장

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // 각 코스는 하나의 유저와 연관됨

    // 기본 생성자
    public Course() {}

    // 수정된 생성자: contentIds 포함
    public Course(String courseName, List<String> contentIds, User user) {
        this.courseName = courseName;
        this.contentIds = contentIds;  // contentIds 초기화 추가
        this.user = user;
    }

    // 관광지 contentId 추가 메서드
    public void addContentId(String contentId) {
        this.contentIds.add(contentId);
    }

    // 관광지 contentId 삭제 메서드
    public void removeContentId(String contentId) {
        this.contentIds.remove(contentId);
    }
}
