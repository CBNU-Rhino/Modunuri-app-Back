package app.app.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;

    // 관심 관광지 저장을 위한 필드
    @ElementCollection
    @CollectionTable(name = "user_favorite_contents", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "content_id")
    private List<String> favoriteContents = new ArrayList<>();

    // 기본 생성자
    public User() {}

    // 생성자
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // 관심 관광지 추가 메서드
    public void addFavoriteContent(String contentId) {
        this.favoriteContents.add(contentId);
    }

    // 관심 관광지 제거 메서드
    public void removeFavoriteContent(String contentId) {
        this.favoriteContents.remove(contentId);
    }
}
