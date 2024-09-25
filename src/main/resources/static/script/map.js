// 모달 관련 변수 전역 선언
let modal, modalBackdrop, modalClose;

// 지도 및 관련 변수 초기화
var mapContainer = document.getElementById('map'), // HTML에서 'map'이라는 ID를 가진 요소를 찾음
    mapOption = {
        center: new kakao.maps.LatLng(37.5665, 126.9780), // 서울 시청의 위도와 경도 좌표로 지도 중심 설정
        level: 8 // 지도 확대 수준 설정 (숫자가 클수록 지도가 더 멀리 보임)
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 카카오 지도 객체 생성
var scheduleRoutes = {}; // 관광지 경로를 저장할 객체, 키는 관광지 ID
var markers = {}; // 지도 위에 표시할 마커들을 저장할 객체, 관광지 ID로 관리
var currentPathPolyline = null; // 현재 경로를 표시하는 폴리라인 객체
var favoritePlaces = {}; // 사용자가 관심 등록한 관광지 정보를 저장하는 객체

// 저장된 관광지 목록 불러오기
function loadSavedPlaces() {
    fetch('/users/getFavoriteContents') // 서버에서 관심 관광지 목록을 가져옴
        .then(response => response.json()) // 응답을 JSON 형식으로 변환
        .then(favoritePlacesData => {
            favoritePlaces = favoritePlacesData; // 가져온 데이터로 favoritePlaces 객체를 업데이트
            Object.keys(favoritePlaces).forEach(contentId => { // 각 관광지 ID에 대해 정보를 가져옴
                const contentTypeId = favoritePlaces[contentId];
                fetch(`/touristSpot/Json/tourist-information?contentId=${contentId}&contentTypeId=${contentTypeId}`) // 관광지 상세 정보를 API에서 요청
                    .then(response => response.json())
                    .then(data => {
                        displaySavedPlace(data.touristDetail); // 관광지 정보를 화면에 표시
                    })
                    .catch(error => console.error("Error fetching tourist information:", error)); // 에러 처리
            });
        })
        .catch(error => console.error('Error loading saved places:', error)); // 에러 처리
}

// 저장된 관광지 정보를 박스로 표시
function displaySavedPlace(touristDetail) {
    const savedPlaces = document.querySelector('#saved-places'); // 저장된 관광지를 표시할 HTML 요소 선택
    const placeBox = document.createElement('div'); // 새로운 div 요소 생성
    placeBox.classList.add('place-box'); // 'place-box' 클래스를 추가
    placeBox.setAttribute('draggable', 'true'); // 드래그 가능 속성 추가
    placeBox.setAttribute('ondragstart', 'drag(event)'); // 드래그 시작 시 실행할 이벤트 설정
    placeBox.id = `place-${touristDetail.contentid}`; // 관광지 ID로 박스에 고유 ID를 부여

    // 관광지의 이미지나 기본 이미지 설정
    const imageUrl = touristDetail.firstimage || '/images/placeholder.jpg';
    const title = touristDetail.title || "제목 없음"; // 제목이 없을 경우 "제목 없음"으로 표시
    const address = touristDetail.addr1 || "주소 없음"; // 주소가 없을 경우 "주소 없음"으로 표시

    // 박스 내부에 HTML로 관광지 정보 추가
    placeBox.innerHTML = `
        <div class="place-info">
            <img src="${imageUrl}" alt="${title}" class="place-image" />
            <div class="place-text">
                <h4>${title}</h4>
                <p>${address}</p>
                <button onclick="openTouristModal('${touristDetail.contentid}', '${touristDetail.contenttypeid}')">상세정보</button>
            </div>
        </div>
    `;

    savedPlaces.appendChild(placeBox); // 생성된 박스를 savedPlaces 영역에 추가
}

// 마커 추가 및 경로 그리기
function addMarkerAndRoute(touristDetail) {
    const coords = new kakao.maps.LatLng(touristDetail.mapy, touristDetail.mapx); // 관광지의 좌표를 설정
    const placeId = touristDetail.contentid; // 관광지 ID를 가져옴

    // 마커 생성
    const marker = new kakao.maps.Marker({
        map: map,
        position: coords // 마커 위치 설정
    });

    // 인포윈도우 생성 (마커 클릭 시 관광지 이름을 표시)
    var infowindow = new kakao.maps.InfoWindow({
        content: `<div style="padding:5px;">${touristDetail.title}</div>` // 관광지 이름 표시
    });

    infowindow.open(map, marker); // 마커 위에 인포윈도우 표시

    // 경로와 마커를 저장 객체에 추가 (ID로 관리)
    scheduleRoutes[placeId] = coords; // 경로 객체에 관광지 좌표 저장
    markers[placeId] = marker; // 마커 객체에 마커 저장
}

// 마커 제거 함수
function removeMarker(placeId) {
    if (markers[placeId]) { // 해당 ID에 대한 마커가 존재하는지 확인
        markers[placeId].setMap(null); // 지도에서 마커 제거
        delete markers[placeId]; // 마커 객체에서 삭제
        delete scheduleRoutes[placeId]; // 경로 객체에서도 삭제
    }
}

// 자동차 경로를 가져오는 함수 (카카오 내비 API 사용)
async function getCarDirection() {
    const REST_API_KEY = '9e7786142c02ceecc7551f66b0d94383'; // 카카오 내비 API 키
    const url = 'https://apis-navi.kakaomobility.com/v1/directions'; // 경로 요청 API 엔드포인트

    // 일정 섹션에서 관광지 ID 순서대로 가져오기
    const scheduleContainer = document.getElementById('schedule');
    const schedulePlaceIds = Array.from(scheduleContainer.children) // 일정 영역에 추가된 장소 ID를 가져옴
        .map(placeElement => placeElement.id.split('-')[1]); // place-1234 형식에서 숫자 ID 추출

    if (schedulePlaceIds.length < 2) { // 두 개 이상의 장소가 있어야 경로를 계산할 수 있음
        console.error("경로를 계산하려면 최소한 두 개의 장소가 필요합니다.");
        return;
    }

    const fullLinePath = []; // 전체 경로를 저장할 배열

    for (let i = 0; i < schedulePlaceIds.length - 1; i++) {
        const placeId1 = schedulePlaceIds[i];
        const placeId2 = schedulePlaceIds[i + 1];

        // 출발지와 도착지 좌표를 경로 배열에서 가져옴
        const origin = `${scheduleRoutes[placeId1].getLng()},${scheduleRoutes[placeId1].getLat()}`;
        const destination = `${scheduleRoutes[placeId2].getLng()},${scheduleRoutes[placeId2].getLat()}`;

        const queryParams = new URLSearchParams({
            origin: origin,
            destination: destination
        });

        const headers = {
            Authorization: `KakaoAK ${REST_API_KEY}`, // API 키를 헤더에 포함
            'Content-Type': 'application/json'
        };

        const requestUrl = `${url}?${queryParams}`; // 완성된 URL

        try {
            const response = await fetch(requestUrl, { method: 'GET', headers: headers });

            if (!response.ok) {
                throw new Error(`HTTP 오류! 상태 코드: ${response.status}`);
            }

            const data = await response.json();

            // 경로의 좌표 데이터를 가져옴
            data.routes[0].sections[0].roads.forEach(road => {
                road.vertexes.forEach((vertex, index) => {
                    if (index % 2 === 0) { // 좌표가 (위도, 경도) 쌍으로 구성되어 있어 홀수 번째 좌표는 생략
                        fullLinePath.push(new kakao.maps.LatLng(road.vertexes[index + 1], road.vertexes[index]));
                    }
                });
            });
        } catch (error) {
            console.error('오류 발생:', error); // 오류 발생 시 로그
        }
    }

    // 이전 경로 지우기
    if (currentPathPolyline) {
        currentPathPolyline.setMap(null); // 지도에서 폴리라인 제거
    }

    // 새로운 경로 그리기
    currentPathPolyline = new kakao.maps.Polyline({
        path: fullLinePath, // 경로 설정
        strokeWeight: 5, // 선 두께
        strokeColor: '#FF0000', // 선 색깔
        strokeOpacity: 0.7, // 선 투명도
        strokeStyle: 'solid' // 선 스타일
    });

    currentPathPolyline.setMap(map); // 지도에 경로 표시
}

// 드래그 앤 드롭 기능 (드래그 가능 설정)
function allowDrop(ev) {
    ev.preventDefault(); // 드롭을 허용하기 위해 기본 동작을 막음
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id); // 드래그된 요소의 ID 저장
}

// 드래그 중인 항목을 어디에 놓을지 계산하는 함수
function getDragAfterElement(container, y) {
    const draggableElements = [...container.querySelectorAll(".place-box:not(.dragging)")];
    // 드래그 중인 항목을 놓을 위치를 찾기 위한 계산
    return draggableElements.reduce((closest, child) => {
        const box = child.getBoundingClientRect(); // 각 항목의 위치와 크기를 가져옴
        const offset = y - box.top - box.height / 2; // 드래그 위치와 리스트 항목 사이의 거리 계산

        // offset이 0보다 작고, 가장 가까운 항목이면 업데이트
        if (offset < 0 && offset > closest.offset) {
            return { offset: offset, element: child };
        } else {
            return closest;
        }
    }, { offset: Number.NEGATIVE_INFINITY }).element; // 반환값: 가장 가까운 항목
}

// 드롭 시 동작 정의
function drop(ev) {
    ev.preventDefault(); // 기본 동작을 막음
    const data = ev.dataTransfer.getData("text"); // 드래그된 요소의 ID 가져옴
    const targetContainer = ev.target; // 드롭된 영역 가져오기
    const placeId = data.split('-')[1]; // place-1234 형식에서 관광지 ID 추출

    if (targetContainer.classList.contains('drop-area')) { // 드롭된 영역이 지정된 'drop-area'인지 확인
        const placeElement = document.getElementById(data); // 드래그된 요소 가져오기
        if (placeElement) {
            targetContainer.appendChild(placeElement); // 드래그된 요소를 드롭된 영역에 추가

            if (targetContainer.id === 'saved-places') {
                removeMarker(placeId); // 저장된 장소 영역에 드롭하면 마커 제거
            } else {
                // 드래그된 장소가 일정 영역에 추가될 경우, 마커 추가
                fetch(`/touristSpot/Json/tourist-information?contentId=${placeId}&contentTypeId=${favoritePlaces[placeId]}`)
                    .then(response => response.json())
                    .then(data => {
                        addMarkerAndRoute(data.touristDetail); // 마커 및 경로 추가
                        const coords = new kakao.maps.LatLng(data.touristDetail.mapy, data.touristDetail.mapx);
                        map.setCenter(coords); // 지도 중심을 해당 관광지의 좌표로 이동
                    })
                    .catch(error => console.error("Error fetching tourist information:", error));
            }
        } else {
            console.error("Invalid element ID:", data); // 요소가 없을 때의 오류 처리
        }
    }
}

// 경로 지우기 기능
function clearRoute() {
    if (currentPathPolyline) {
        currentPathPolyline.setMap(null); // 지도에서 경로 제거
    }
}

// 페이지 로드 시 저장된 관광지 목록 불러오기 및 모달 설정
document.addEventListener('DOMContentLoaded', function () {
    loadSavedPlaces(); // 저장된 장소 로드

    // 드래그 가능한 요소에 드래그 이벤트 추가
    document.querySelectorAll('.place-box').forEach(placeBox => {
        placeBox.addEventListener('dragstart', onDragStart); // 드래그 시작 시
        placeBox.addEventListener('dragover', onDragOver);   // 드래그가 진행될 때
        placeBox.addEventListener('dragenter', onDragEnter); // 드래그가 다른 요소 위로 들어갈 때
        placeBox.addEventListener('drop', onDrop); // 드래그를 마치고 드롭할 때
    });

    // 드롭 가능 영역에 드롭 이벤트 추가
    document.querySelectorAll('.drop-area').forEach(dropArea => {
        dropArea.addEventListener('dragover', allowDrop); // 드롭 가능 영역에서 드래그를 허용
        dropArea.addEventListener('drop', drop); // 드래그 완료 시 실행
    });

    // 모달 관련 요소 선택
    modal = document.querySelector('.modal');
    modalBackdrop = document.querySelector('.modal-backdrop');
    modalClose = document.querySelector('.close_btn');

    // 모달 닫기 버튼 클릭 시 모달 닫기
    modalClose.addEventListener('click', function() {
        modal.classList.remove('on');
        modalBackdrop.classList.remove('on');
    });

    // 모달 외부 클릭 시 모달 닫기
    modalBackdrop.addEventListener('click', function() {
        modal.classList.remove('on');
        modalBackdrop.classList.remove('on');
    });
});

// 관광지 상세정보 모달 열기
function openTouristModal(contentId, contentTypeId) {
    fetch(`/touristSpot/Json/tourist-information?contentId=${contentId}&contentTypeId=${contentTypeId}`) // 관광지 상세 정보 요청
        .then(response => response.json())
        .then(data => {
            const touristDetail = data.touristDetail;
            const accessibilityInfo = data.accessibilityInfo;

            // 모달의 제목, 이미지, 설명 설정
            document.getElementById('modal-title').innerText = touristDetail.title || "제목 없음";
            document.getElementById('modal-image').src = touristDetail.firstimage || '/images/placeholder.jpg';
            document.getElementById('modal-overview').innerText = touristDetail.overview || "설명 정보 없음";

            // 오른쪽 정보 설정
            document.getElementById('modal-address').innerText = touristDetail.addr1 || "주소 정보 없음";
            document.getElementById('modal-tel').innerText = touristDetail.tel || "전화번호 정보 없음";
            document.getElementById('modal-homepage').innerHTML = touristDetail.homepage || "홈페이지 정보 없음";
            document.getElementById('modal-parking').innerText = accessibilityInfo.parking || "주차 정보 없음";
            document.getElementById('modal-restroom').innerText = accessibilityInfo.restroom || "장애인 화장실 정보 없음";
            document.getElementById('modal-wheelchair').innerText = accessibilityInfo.wheelchair || "휠체어 정보 없음";
            document.getElementById('modal-stroller').innerText = accessibilityInfo.stroller || "유모차 정보 없음";
            document.getElementById('modal-blindhandicapetc').innerText = accessibilityInfo.blindhandicapetc || "시각장애인 편의 정보 없음";
            document.getElementById('modal-elevator').innerText = accessibilityInfo.elevator || "엘리베이터 정보 없음";

            // 모달 및 배경 활성화
            modal.classList.add('on');
            modalBackdrop.classList.add('on');
        })
        .catch(error => console.error('Error fetching tourist detail:', error));
}

// 드래그 중인 항목을 어디에 놓을지 계산하는 함수
function getDragAfterElement(container, y) {
    // 드래그 중이지 않은 모든 요소들을 가져옴
    const draggableElements = [...container.querySelectorAll(".place-box:not(.dragging)")];

    // 드래그 중인 항목을 놓을 위치를 찾기 위한 계산
    return draggableElements.reduce((closest, child) => {
        const box = child.getBoundingClientRect(); // 각 항목의 위치와 크기를 가져옴
        const offset = y - box.top - box.height / 2; // 드래그 위치와 리스트 항목 사이의 거리 계산

        // offset이 0보다 작고, 가장 가까운 항목이면 업데이트
        if (offset < 0 && offset > closest.offset) {
            return { offset: offset, element: child };
        } else {
            return closest;
        }
    }, { offset: Number.NEGATIVE_INFINITY }).element; // 반환값: 가장 가까운 항목
}

// 드래그 시작 시 선택된 요소 저장
let selectedElement = null; // 선택된 요소 저장 변수
let selectedElementIndex = null; // 선택된 요소의 인덱스 저장 변수

function onDragStart(event) {
    selectedElement = event.target; // 드래그된 요소를 저장
    selectedElementIndex = [...selectedElement.parentNode.children].indexOf(selectedElement); // 선택된 요소의 인덱스 저장
    selectedElement.classList.add('dragging'); // 드래그 시 시각적 효과 적용
}

function onDragOver(event) {
    event.preventDefault(); // 드롭을 허용하기 위해 기본 동작을 막음
}

function onDrop(event) {
    event.preventDefault(); // 기본 동작 막기

    const targetElement = event.target.closest('.place-box'); // 드롭된 대상 요소 찾기
    if (!targetElement || targetElement === selectedElement) return; // 유효하지 않은 타겟이면 무시

    const targetElementIndex = [...targetElement.parentNode.children].indexOf(targetElement); // 타겟 요소의 인덱스 가져오기
    const selectedElementIndex = [...selectedElement.parentNode.children].indexOf(selectedElement); // 선택된 요소의 인덱스 가져오기

    // 선택된 요소가 타겟 요소 위나 아래로 이동하는지 확인
    if (targetElementIndex > selectedElementIndex) {
        targetElement.after(selectedElement); // 타겟 요소 아래로 이동
        moveElement(targetElement, selectedElement, "down"); // 이동 애니메이션 적용
    } else {
        targetElement.before(selectedElement); // 타겟 요소 위로 이동
        moveElement(targetElement, selectedElement, "up"); // 이동 애니메이션 적용
    }

    // 애니메이션 리셋 후 초기화
    requestAnimationFrame(() => {
        selectedElement.classList.remove('dragging'); // 드래그 스타일 제거
    });
}

// 두 요소의 시각적 이동 애니메이션 처리
function moveElement(targetElement, selectedElement, direction) {
    const targetElementTop = targetElement.getBoundingClientRect().top;
    const selectedElementTop = selectedElement.getBoundingClientRect().top;
    const distance = targetElementTop - selectedElementTop;

    // 애니메이션을 통해 선택된 요소와 타겟 요소의 위치 변경
    if (direction === "down") {
        selectedElement.style.transform = `translateY(${distance}px)`;
        targetElement.style.transform = `translateY(${-distance}px)`;
    } else {
        selectedElement.style.transform = `translateY(${distance}px)`;
        targetElement.style.transform = `translateY(${-distance}px)`;
    }

    // 일정 시간이 지나면 위치 고정
    setTimeout(() => {
        selectedElement.style.transform = '';
        targetElement.style.transform = '';
    }, 300); // 300ms 후 애니메이션 종료
}

// 두 요소의 위치를 바꾸는 함수
function changePosition(targetElement) {
    if (!targetElement) {
        console.error("targetElement is not defined."); // 대상 요소가 없으면 오류 처리
        return;
    }

    const targetElementTop = targetElement.getBoundingClientRect().top;
    const selectedElementTop = selectedElement.getBoundingClientRect().top;

    const distance = targetElementTop - selectedElementTop;

    // 애니메이션을 통해 두 요소가 움직이는 것을 시각적으로 표현
    selectedElement.style.transform = `translateY(${distance}px)`;
    targetElement.style.transform = `translateY(${-distance}px)`;

    // 애니메이션 적용
    addAnimation(selectedElement);
    addAnimation(targetElement);

    // 일정 시간이 지나면 실제 DOM 위치를 변경
    setTimeout(() => {
        if (distance > 0) {
            targetElement.after(selectedElement); // 타겟 요소 뒤로 선택된 요소 이동
        } else {
            targetElement.before(selectedElement); // 타겟 요소 앞으로 선택된 요소 이동
        }

        // 위치 변경 후 transform 속성을 초기화
        selectedElement.style.transform = '';
        targetElement.style.transform = '';
    }, 300); // 애니메이션 지속 시간
}

// 애니메이션 클래스 적용 함수
function addAnimation(element) {
    element.classList.add('animation'); // 애니메이션 적용
}
