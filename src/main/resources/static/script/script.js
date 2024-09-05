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

let currentIndex = 0;
const slides = document.querySelector('.slides');
const dots = document.querySelectorAll('.dot');
const totalSlides = dots.length;

function showSlide(index) {
    currentIndex = (index + totalSlides) % totalSlides; // 인덱스가 음수이거나 슬라이드 수를 넘어가는 경우를 처리
    const offset = -currentIndex * 100; // 슬라이드 오프셋 계산
    slides.style.transform = `translateX(${offset}%)`; // 슬라이드를 이동시킴
    updateDots(); // 현재 슬라이드에 맞는 점 업데이트
}

function updateDots() {
    dots.forEach((dot, index) => {
        dot.classList.toggle('active', index === currentIndex); // 현재 슬라이드에 해당하는 점만 활성화
    });
}

// 각 점에 대한 클릭 이벤트 처리
dots.forEach((dot, index) => {
    dot.addEventListener('click', () => {
        showSlide(index); // 해당 점을 클릭하면 해당 슬라이드로 이동
    });
});

// 자동 슬라이드 넘기기
function showNextSlide() {
    showSlide(currentIndex + 1); // 다음 슬라이드로 이동
}

// 슬라이드 자동 변경 간격 설정 (5초)
const slideInterval = setInterval(showNextSlide, 5000);

// 마우스를 슬라이드 위에 올렸을 때 자동 슬라이드 멈춤
slides.addEventListener('mouseenter', () => clearInterval(slideInterval));
// 마우스를 슬라이드에서 떼면 다시 자동 슬라이드 시작
slides.addEventListener('mouseleave', () => setInterval(showNextSlide, 5000));

updateDots(); // 페이지가 로드되면 처음에 점을 업데이트

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
<<<<<<< HEAD







=======
>>>>>>> 8a2e7deb4f032a095336b6f63c4739ccc9e0c48a
