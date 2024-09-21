document.addEventListener('DOMContentLoaded', function () {
    const pages = [
        document.getElementById('mainPage'),
        document.getElementById('durationPage'),
        document.getElementById('regionPage'),
        document.getElementById('barrierPage'),
        document.getElementById('selectPage'),
        document.getElementById('planPage'),
        document.getElementById('completionPage')
    ];

    let currentPageIndex = 0;

    // 선택된 항목들을 저장할 객체
    let travelPlan = {
        duration: '',
        region: '',
        barriers: [],
        selectedPlaces: [],
        tripName: ''
    };

    const userId = "사용자"; // 예시로 사용자 ID를 "사용자"로 설정, 실제로는 로그인 정보를 사용

    // 권역별 도시 목록
    const regionCities = {
        수도권: ["서울", "인천", "수원", "파주", "용인"],
        강원권: ["속초", "강릉", "춘천", "원주", "평창"],
        충청권: ["대전", "청주", "천안"],
        전라권: ["광주", "여수", "전주", "순천", "군산"],
        경상권: ["대구", "부산", "울산", "경주", "포항", "거제", "창원"],
        제주권: ["제주", "서귀포"]
    };

    // 페이지 초기화 - 첫 번째 페이지만 보이게 함
    function showPage(index) {
        pages.forEach((page, i) => {
            page.style.display = i === index ? 'block' : 'none';
        });
    }

    // 처음에는 메인 페이지만 보이도록 설정
    showPage(currentPageIndex);


    // next-btn 클릭 시 다음 페이지로 이동
    document.querySelectorAll('.next-btn').forEach(btn => {
        btn.addEventListener('click', function () {
            // selectedRegion이 null일 경우 페이지 전환을 막음
            if (!selectedRegion && currentPageIndex === 1) {
                alert("권역을 선택해주세요.");
                return; // 권역이 선택되지 않으면 페이지 전환하지 않음
            }

            if (currentPageIndex < pages.length - 1) {
                currentPageIndex++;
                showPage(currentPageIndex);
                if (currentPageIndex === 2) {
                    // 2번 지역 선택 페이지에서 버튼 업데이트
                    updateRegionButtons(selectedRegion);
                }
                if (currentPageIndex === 4) {
                    // 4번 페이지: 관광지 리스트 생성
                    generateTouristSpots();
                }
                if (currentPageIndex === 5) {
                    // 5번 페이지: 선택한 관광지 정렬하기
                    generateSortableList();
                    initializeDragEvents(); // 드래그 기능 활성화
                }
                if (currentPageIndex === 6) {
                    // 여행 완료 페이지로 이동 시 여행 이름 설정
                    finalizeTripName();
                }
            }
        });
    });

    // 이전 버튼 클릭 시 이전 페이지로 이동
    document.querySelector('.back-button').addEventListener('click', function () {
        if (currentPageIndex > 0) {
            currentPageIndex--;
            showPage(currentPageIndex);
        }
    });


    // 권역 선택 관련 스크립트 추가
    let selectedRegion = null;

    // 모든 권역 동그라미 요소 선택
    const regionCircles = document.querySelectorAll('.circle');

    // '다음' 버튼 선택
    const nextBtn = document.querySelector('.next-btn');

    // 각 권역 클릭 시 동작 설정
    regionCircles.forEach(circle => {
        circle.addEventListener('click', function () {
            // 선택된 권역 저장
            selectedRegion = this.getAttribute('data-region');

            // 모든 동그라미의 스타일을 초기화
            regionCircles.forEach(c => {
                c.style.backgroundColor = 'rgba(0, 123, 255, 0.5)';
            });

            // 클릭된 동그라미의 스타일 변경
            this.style.backgroundColor = 'rgba(40, 167, 69, 0.7)'; // 선택된 권역은 초록색으로

            // 다음 버튼 활성화
            nextBtn.disabled = false;
            nextBtn.classList.add('active');

        });
    });

    // 지역 선택 페이지의 버튼을 업데이트하는 함수
    function updateRegionButtons(region) {
        const regionButtonsContainer = document.querySelector('.region-buttons');
        regionButtonsContainer.innerHTML = '';  // 기존 버튼 초기화

        const cities = regionCities[region];  // 선택된 권역에 맞는 도시 목록 가져오기
        cities.forEach(city => {
            const button = document.createElement('button');
            button.classList.add('region-btn');
            button.textContent = city;

            // 버튼 클릭 이벤트 추가
            button.addEventListener('click', function () {
                travelPlan.region = city;
                document.querySelectorAll('.region-btn').forEach(btn => {
                    btn.style.backgroundColor = '#f9f9f9';
                    btn.style.color = '#555';
                });
                this.style.backgroundColor = '#89B873';
                this.style.color = '#ffffff';
            });

            regionButtonsContainer.appendChild(button);
        });
    }

    // 무장애 시설 선택 버튼 클릭 시 이벤트 처리
    const barrierButtons = document.querySelectorAll('.barrier-btn');
    barrierButtons.forEach(button => {
        button.addEventListener('click', function () {
            const selectedBarrier = this.textContent;
            const index = travelPlan.barriers.indexOf(selectedBarrier);
            if (index === -1) {
                travelPlan.barriers.push(selectedBarrier);
                this.style.backgroundColor = '#89B873';
                this.style.color = '#ffffff';
            } else {
                travelPlan.barriers.splice(index, 1);
                this.style.backgroundColor = '#f9f9f9';
                this.style.color = '#555';
            }
        });
    });


    // 4번 페이지: 관광지 리스트 생성
    function generateTouristSpots() {
        const touristSpots = [
            "관광지 1", "관광지 2", "관광지 3",
            "관광지 4", "관광지 5", "관광지 6"
        ]; // API에서 데이터를 받아오는 로직으로 대체 가능
        const selectBox = document.querySelector('.select-box');
        selectBox.innerHTML = ''; // 기존 항목 초기화
        touristSpots.forEach(spot => {
            const spotElement = document.createElement('div');
            spotElement.classList.add('spot-item');
            spotElement.textContent = spot;

            spotElement.addEventListener('click', function () {
                if (travelPlan.selectedPlaces.includes(spot)) {
                    // 선택 취소: 선택했던 관광지를 배열에서 제거
                    travelPlan.selectedPlaces = travelPlan.selectedPlaces.filter(item => item !== spot);
                    this.style.backgroundColor = '#f9f9f9';  // 초기 배경색으로 변경
                    this.style.color = '#555';  // 초기 글자색으로 변경
                } else {
                    // 선택된 관광지를 배열에 추가
                    travelPlan.selectedPlaces.push(spot);
                    this.style.backgroundColor = '#89B873';  // 선택된 상태의 배경색 (초록색)
                    this.style.color = '#ffffff';  // 선택된 상태의 글자색 (흰색)
                }
            });

            selectBox.appendChild(spotElement);
        });
    }

    // 5번 페이지: 선택한 관광지 목록을 드래그 가능한 리스트로 표시
    function generateSortableList() {
        const sortableList = document.getElementById('sortable');
        sortableList.innerHTML = ''; // 기존 항목 초기화
        travelPlan.selectedPlaces.forEach((place, index) => {
            const listItem = document.createElement('li');
            listItem.classList.add('list-item');
            listItem.setAttribute('draggable', 'true');
            listItem.innerHTML = `
                <div class="content">
                    <div class="left-info">
                        <div class="icon"><img src="path-to-icon.png" alt="icon"></div>
                        <div>
                            <strong>${index + 1}. ${place}</strong>
                            <p>설명 또는 정보</p>
                        </div>
                    </div>
                    <div class="drag-handle">
                        <img src="path-to-drag-handle.png" alt="drag handle">
                    </div>
                </div>
            `;

            sortableList.appendChild(listItem);
        });

        // 각 항목에 드래그 이벤트를 추가
        initializeDragEvents();
    }

    // 드래그 앤 드롭 기능 활성화
    function initializeDragEvents() {
        let draggedItem = null;

        document.querySelectorAll('.list-item').forEach(item => {
            const dragHandle = item.querySelector('.drag-handle');

            dragHandle.addEventListener('mousedown', function (event) {
                draggedItem = item;
                item.classList.add('dragging');

                document.addEventListener('mousemove', onMouseMove);
                document.addEventListener('mouseup', onMouseUp);
            });

            function onMouseMove(event) {
                if (!draggedItem) return;

                draggedItem.style.position = 'absolute';
                draggedItem.style.top = `${event.clientY - draggedItem.offsetHeight / 2}px`;
                draggedItem.style.left = `${event.clientX - draggedItem.offsetWidth / 2}px`;
            }

            function onMouseUp() {
                if (!draggedItem) return;

                draggedItem.classList.remove('dragging');
                draggedItem.style.position = '';
                draggedItem.style.top = '';
                draggedItem.style.left = '';
                draggedItem = null;

                document.removeEventListener('mousemove', onMouseMove);
                document.removeEventListener('mouseup', onMouseUp);
            }
        });
    }

    // 6번 페이지: 여행 이름 설정 및 여행 완료 처리
    function finalizeTripName() {
        const tripNameInput = document.getElementById('tripName').value;
        if (tripNameInput.trim() === "") {
            travelPlan.tripName = `${userId}의 멋진 여행`; // 기본 여행 이름 설정
        } else {
            travelPlan.tripName = tripNameInput;
        }

        // 여행 이름과 정리된 여행 정보를 출력하거나 저장
        console.log('최종 여행 계획:', travelPlan);
    }
});