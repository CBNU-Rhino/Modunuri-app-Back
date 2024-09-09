document.addEventListener('DOMContentLoaded', function () {
    const pages = [
        document.getElementById('mainPage'),
        document.getElementById('durationPage'),
        document.getElementById('regionPage'),
        document.getElementById('barrierPage'),
        document.getElementById('selectPage'),
        document.getElementById('completionPage')
    ];

    let currentPageIndex = 0;

    // 페이지 초기화 - 첫 번째 페이지만 보이게 함
    function showPage(index) {
        pages.forEach((page, i) => {
            if (i === index) {
                page.style.display = 'block';
            } else {
                page.style.display = 'none';
            }
        });
    }

    // 처음에는 메인 페이지만 보이도록 설정
    showPage(currentPageIndex);

    // next-btn 클릭 시 다음 페이지로 이동
    document.querySelectorAll('.next-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            if (currentPageIndex < pages.length - 1) {
                currentPageIndex++;
                showPage(currentPageIndex);
            }
        });
    });

    // 여행 기간 버튼 클릭 시 이벤트 처리
    const durationButtons = document.querySelectorAll('.duration-btn');
    durationButtons.forEach(button => {
        button.addEventListener('click', function () {
            durationButtons.forEach(btn => {
                btn.style.backgroundColor = '#f9f9f9';
                btn.style.color = '#555';
            });

            this.style.backgroundColor = '#007bff';
            this.style.color = '#ffffff';
            selectedDuration = this.textContent;
            console.log('선택된 여행 기간:', selectedDuration);
        });
    });

    // 지역 선택 버튼 클릭 시 이벤트 처리
    const regionButtons = document.querySelectorAll('.region-btn');
    regionButtons.forEach(button => {
        button.addEventListener('click', function () {
            regionButtons.forEach(btn => {
                btn.style.backgroundColor = '#f9f9f9';
                btn.style.color = '#555';
            });

            this.style.backgroundColor = '#007bff';
            this.style.color = '#ffffff';
            selectedRegion = this.textContent;
            console.log('선택된 지역:', selectedRegion);
        });
    });

    // 무장애 선택 버튼 클릭 시 이벤트 처리
    const barrierButtons = document.querySelectorAll('.barrier-btn');
    barrierButtons.forEach(button => {
        button.addEventListener('click', function () {
            barrierButtons.forEach(btn => {
                btn.style.backgroundColor = '#f9f9f9';
                btn.style.color = '#555';
            });

            this.style.backgroundColor = '#007bff';
            this.style.color = '#ffffff';
            selectedbarrier = this.textContent;
            console.log('선택된 여행 기간:', selectedDuration);
        });
    });

    // 지역 선택 버튼 클릭 시 이벤트 처리
    const selectButtons = document.querySelectorAll('.select-btn');
    selectButtons.forEach(button => {
        button.addEventListener('click', function () {
            selectButtons.forEach(btn => {
                btn.style.backgroundColor = '#f9f9f9';
                btn.style.color = '#555';
            });

            this.style.backgroundColor = '#007bff';
            this.style.color = '#ffffff';
            selectedRegion = this.textContent;
            console.log('선택된 지역:', selectedRegion);
        });
    });

    // 페이지 전환 시 선택한 정보를 저장하는 로직 추가 (필요 시)
});
