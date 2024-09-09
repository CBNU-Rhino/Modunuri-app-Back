package app.app.course.Service;

import app.app.course.Course;
import app.app.course.Repository.CourseRepository;
import app.app.user.User;
import app.app.user.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    // 코스 추가
    public Course addCourse(Long userId, String courseName, List<String> contentIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // Course 생성 시 contentIds도 전달하도록 수정
        Course course = new Course(courseName, contentIds, user);
        return courseRepository.save(course);
    }

    // 유저의 코스 목록 조회
    public List<Course> getUserCourses(Long userId) {
        return courseRepository.findByUserId(userId);
    }

    // 코스 저장
    public Course save(Course course) {
        return courseRepository.save(course);
    }
}
