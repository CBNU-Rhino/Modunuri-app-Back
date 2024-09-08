// script.js
document.querySelector('.search-input').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        event.preventDefault(); // 기본 폼 제출을 막음
        if (this.value.trim() !== '') {
            this.form.submit(); // 폼 제출
        } else {
            alert('Please enter a search term'); // 입력 값이 없을 때 경고창 띄우기
        }
    }
});

document.getElementById('signupForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 폼 제출 동작을 막음

    // 유효성 검사 로직을 여기에 추가할 수 있습니다.

    // 서버에 데이터를 전송하거나 추가 처리를 한 후
    // 성공 시 signup_complete.html로 이동
    window.location.href = 'signup_complete.html';
});
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 기본 폼 제출 동작을 막음

    // 로그인 정보를 가져옴
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // 여기서 서버에 로그인 요청을 보내고 응답을 처리합니다.
    // 예를 들어, Ajax를 사용하여 서버에 요청을 보낼 수 있습니다.

    // 이 예시에서는 서버 요청 없이 바로 리다이렉트합니다.
    // 로그인 성공 시 index.html로 리다이렉트
    if (username === "testuser" && password === "password") {
        // 가정: username이 "testuser"이고, password가 "password"일 때 로그인 성공
        window.location.href = "index.html";
    } else {
        alert("Invalid username or password"); // 로그인 실패 시 경고창 표시
    }
});

// scripts.js
let currentIndex = 0;
const slides = document.querySelector('.slides');
const dots = document.querySelectorAll('.dot');
const totalSlides = dots.length;

function showSlide(index) {
    currentIndex = (index + totalSlides) % totalSlides;
    const offset = -currentIndex * 100;
    slides.style.transform = `translateX(${offset}%)`;
    updateDots();
}

function updateDots() {
    dots.forEach((dot, index) => {
        dot.classList.toggle('active', index === currentIndex);
    });
}

dots.forEach((dot, index) => {
    dot.addEventListener('click', () => {
        showSlide(index);
    });
});

function showNextSlide() {
    showSlide(currentIndex + 1);
}

// 슬라이드 자동 변경 간격 설정 (5초)
setInterval(showNextSlide, 5000);

updateDots();
//
document.addEventListener('DOMContentLoaded', function() {
    // 서버에서 게시물 목록 가져오기
    fetch('/api/posts')
        .then(response => response.json())
        .then(posts => {
            const postList = document.getElementById('post-list');
            postList.innerHTML = ''; // 기존 목록 초기화
            console.log(posts); // 게시물 데이터 출력 (확인용)

            if (posts.length === 0) {
                const emptyRow = document.createElement('tr');
                emptyRow.innerHTML = `<td colspan="5" style="text-align: center;">No posts available</td>`;
                postList.appendChild(emptyRow);
            } else {
                posts.forEach(post => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
                            <td>${post.id}</td>
                            <td><a href="/board/post/${post.id}">${post.title}</a></td>
                            <td>${post.author}</td>
                            <td>${new Date(post.createdAt).toLocaleString()}</td>
                            <td>
                                <a href="/board/edit/${post.id}" class="edit-link">Edit</a> |
                                <a href="/board/delete/${post.id}" class="delete-link">Delete</a>
                            </td>
                        `;
                    postList.appendChild(row);
                });
            }
        })
        .catch(error => {
            console.error('Error fetching posts:', error);
            const postList = document.getElementById('post-list');
            const errorRow = document.createElement('tr');
            errorRow.innerHTML = `<td colspan="5" style="text-align: center; color: red;">Failed to load posts</td>`;
            postList.appendChild(errorRow);
        });
});







