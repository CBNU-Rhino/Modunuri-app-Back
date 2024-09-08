document.addEventListener('DOMContentLoaded', function () {
    const openModalBtn = document.getElementById('openModalBtn');
    const durationPage = document.getElementById('durationPage');
    const regionPage = document.getElementById('regionPage');
    const toRegionPageBtns = document.querySelectorAll('#toRegionPageBtn');
    const backToSchedulePageBtn = document.getElementById('backToSchedulePageBtn');

    // 첫 페이지 열기
    openModalBtn.addEventListener('click', () => {
        durationPage.style.display = 'block';
    });

    // 일정 선택 후 지역 선택 페이지로 이동
    toRegionPageBtns.forEach(btn => {
        btn.addEventListener('click', () => {
            durationPage.style.display = 'none'; // 일정 페이지 숨기기
            regionPage.style.display = 'block'; // 지역 선택 페이지 보이기
        });
    });

    // 지역 선택 페이지에서 뒤로 가기
    backToSchedulePageBtn.addEventListener('click', () => {
        regionPage.style.display = 'none';
        durationPage.style.display = 'block';
    });
});