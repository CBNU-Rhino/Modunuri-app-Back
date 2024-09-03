// Search form submit handling
document.querySelector('.search-input').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        if (this.value.trim() === '') {
            event.preventDefault(); // 입력 값이 없을 때만 기본 제출을 막음
            alert('Please enter a search term'); // 입력 값이 없을 때 경고창 띄우기
        }
    }
});

// 회원가입 폼 제출 처리
document.getElementById('signupForm').addEventListener('submit', function() {
    // 유효성 검사 등을 추가할 수 있음
    // 기본 폼 제출을 허용하여 서버로 전송
});

// 로그인 폼 제출 처리
document.getElementById('loginForm').addEventListener('submit', function() {
    // 기본 폼 제출을 허용하여 서버로 전송
});

// 슬라이드 쇼 기능
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
        clearInterval(slideInterval); // 사용자 클릭 시 자동 슬라이드 멈춤
        showSlide(index);
    });
});

function showNextSlide() {
    showSlide(currentIndex + 1);
}

// 슬라이드 자동 변경 간격 설정 (5초)
let slideInterval = setInterval(showNextSlide, 5000);

updateDots();

