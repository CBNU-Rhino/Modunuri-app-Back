// 지도 및 관련 변수 초기화
var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(37.5665, 126.9780), // 서울 시청의 위도와 경도
        level: 3 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 생성
var scheduleRoutes = {}; // 일정 경로 저장 객체 (ID로 관리)
var markers = {}; // 마커 저장 객체 (ID로 관리)
var currentPathPolyline = null; // 경로 폴리라인 저장 객체
var favoritePlaces = {}; // 저장된 관심 관광지 정보 저장 객체

// 저장된 관광지 목록 불러오기
function loadSavedPlaces() {
    fetch('/users/getFavoriteContents')
        .then(response => response.json())
        .then(favoritePlacesData => {
            favoritePlaces = favoritePlacesData;
            Object.keys(favoritePlaces).forEach(contentId => {
                const contentTypeId = favoritePlaces[contentId];
                fetch(`/touristSpot/Json/tourist-information?contentId=${contentId}&contentTypeId=${contentTypeId}`)
                    .then(response => response.json())
                    .then(data => {
                        displaySavedPlace(data.touristDetail);
                    })
                    .catch(error => console.error("Error fetching tourist information:", error));
            });
        })
        .catch(error => console.error('Error loading saved places:', error));
}

// 저장된 관광지 정보를 박스로 표시
function displaySavedPlace(touristDetail) {
    const savedPlaces = document.querySelector('#saved-places');
    const placeBox = document.createElement('div');
    placeBox.classList.add('place-box');
    placeBox.setAttribute('draggable', 'true');
    placeBox.setAttribute('ondragstart', 'drag(event)');
    placeBox.id = `place-${touristDetail.contentid}`;

    const imageUrl = touristDetail.firstimage || '/images/placeholder.png';
    const title = touristDetail.title || "제목 없음";
    const address = touristDetail.addr1 || "주소 없음";

    placeBox.innerHTML = `
        <div class="place-info">
            <img src="${imageUrl}" alt="${title}" class="place-image" />
            <div class="place-text">
                <h4>${title}</h4>
                <p>${address}</p>
            </div>
        </div>
    `;

    savedPlaces.appendChild(placeBox);
}

// 마커 추가 및 경로 그리기
function addMarkerAndRoute(touristDetail) {
    const coords = new kakao.maps.LatLng(touristDetail.mapy, touristDetail.mapx);
    const placeId = touristDetail.contentid; // 관광지 ID

    // 마커 생성
    const marker = new kakao.maps.Marker({
        map: map,
        position: coords
    });

    // 인포윈도우 생성
    var infowindow = new kakao.maps.InfoWindow({
        content: `<div style="padding:5px;">${touristDetail.title}</div>` // 관광지 이름 표시
    });

    infowindow.open(map, marker); // 마커에 인포윈도우 표시

    // 경로와 마커 추가 (ID로 관리)
    scheduleRoutes[placeId] = coords;
    markers[placeId] = marker;
}

// 마커 제거 함수
function removeMarker(placeId) {
    console.log(`removeMarker called for placeId: ${placeId}`); // 로그 추가

    if (markers[placeId]) {
        console.log(`Removing marker formarkers  placeId: ${placeId}`); // 마커 제거 전 로그
        markers[placeId].setMap(null); // 지도에서 마커 제거
        delete markers[placeId]; // 마커 객체에서 삭제
        delete scheduleRoutes[placeId]; // 경로 배열에서도 삭제
        console.log(`Marker and route for placeId: ${placeId} removed.`); // 제거 완료 후 로그
    } else {
        console.log(`No marker found for placeId: ${placeId}`); // 마커가 없을 때의 로그
    }
}


// 자동차 경로를 가져오는 함수 (카카오 내비 API 사용)
async function getCarDirection() {
    const REST_API_KEY = '9e7786142c02ceecc7551f66b0d94383';
    const url = 'https://apis-navi.kakaomobility.com/v1/directions';

    const placeIds = Object.keys(scheduleRoutes);
    if (placeIds.length < 2) {
        console.error("At least two places are required to calculate the route.");
        return;
    }

    // 전체 경로를 저장할 배열
    const fullLinePath = [];

    // 모든 구간의 경로를 순차적으로 계산
    for (let i = 0; i < placeIds.length - 1; i++) {
        const origin = `${scheduleRoutes[placeIds[i]].getLng()},${scheduleRoutes[placeIds[i]].getLat()}`;
        const destination = `${scheduleRoutes[placeIds[i + 1]].getLng()},${scheduleRoutes[placeIds[i + 1]].getLat()}`;

        const queryParams = new URLSearchParams({
            origin: origin,
            destination: destination
        });

        const headers = {
            Authorization: `KakaoAK ${REST_API_KEY}`,
            'Content-Type': 'application/json'
        };

        const requestUrl = `${url}?${queryParams}`;

        try {
            const response = await fetch(requestUrl, {
                method: 'GET',
                headers: headers
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();

            data.routes[0].sections[0].roads.forEach(road => {
                road.vertexes.forEach((vertex, index) => {
                    if (index % 2 === 0) {
                        fullLinePath.push(new kakao.maps.LatLng(road.vertexes[index + 1], road.vertexes[index]));
                    }
                });
            });
        } catch (error) {
            console.error('Error:', error);
        }
    }

    // 이전 경로가 있으면 제거
    if (currentPathPolyline) {
        currentPathPolyline.setMap(null);
    }

    // 계산된 전체 경로를 지도에 그립니다.
    currentPathPolyline = new kakao.maps.Polyline({
        path: fullLinePath,
        strokeWeight: 5,
        strokeColor: '#FF0000',
        strokeOpacity: 0.7,
        strokeStyle: 'solid'
    });

    currentPathPolyline.setMap(map); // 경로 지도에 표시
}

// 드래그 앤 드롭 기능
function allowDrop(ev) {
    ev.preventDefault(); // 기본 동작을 막아 드롭 가능하도록 설정
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id); // 드래그된 요소의 ID 저장
}

function drop(ev) {
    ev.preventDefault(); // 기본 동작을 막음
    const data = ev.dataTransfer.getData("text"); // 드래그된 요소의 ID 가져옴
    const targetContainer = ev.target;
    const placeId = data.split('-')[1]; // place-1234 형식에서 id만 추출

    // 드롭할 수 있는 영역인지 확인
    if (targetContainer.classList.contains('drop-area')) {
        const placeElement = document.getElementById(data);
        if (placeElement) {
            targetContainer.appendChild(placeElement); // 드래그된 요소를 드롭된 영역에 추가

            // 내 저장 목록으로 이동된 경우 지도에서 마커 제거
            if (targetContainer.id === 'saved-places') {
                removeMarker(placeId);
            } else {
                // 일정을 추가하는 경우 마커 및 경로 추가
                fetch(`/touristSpot/Json/tourist-information?contentId=${placeId}&contentTypeId=${favoritePlaces[placeId]}`)
                    .then(response => response.json())
                    .then(data => {
                        addMarkerAndRoute(data.touristDetail); // 마커 및 경로 추가
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
    // 경로만 지우고, 마커와 좌표는 남겨둡니다.
}

// 페이지 로드 시 저장된 관광지 목록 불러오기
document.addEventListener('DOMContentLoaded', function () {
    loadSavedPlaces(); // 저장된 장소 로드

    // 드래그 가능한 요소에 드래그 이벤트 추가
    document.querySelectorAll('.place-box').forEach(placeBox => {
        placeBox.addEventListener('dragstart', onDragStart); // 드래그 시작 시
        placeBox.addEventListener('dragover', onDragOver);   // 드래그가 진행될 때
        placeBox.addEventListener('dragenter', onDragEnter); // 드래그가 다른 요소 위로 들어갈 때
        placeBox.addEventListener('drop', onDrop);
    });


    // 드롭 가능 영역에 드롭 이벤트 추가
    document.querySelectorAll('.drop-area').forEach(dropArea => {
        dropArea.addEventListener('dragover', allowDrop);
        dropArea.addEventListener('drop', drop);
    });
});

// 모달 닫기 기능
function closeModal() {
    document.getElementById('search-result-modal').style.display = "none";
}

// 드래그 시작 시 선택된 요소 저장
let selectedElement = null;
let selectedElementIndex = null;

function onDragStart(event) {
    selectedElement = event.target; // 드래그된 요소 저장
    selectedElementIndex = [...selectedElement.parentNode.children].indexOf(selectedElement); // 선택된 요소의 인덱스를 저장
    selectedElement.classList.add('dragging'); // 드래그 시 스타일 변경
}

function onDragOver(event) {
    event.preventDefault(); // 드롭을 허용하기 위해 기본 동작을 막음
}

function onDrop(event) {
    event.preventDefault(); // 기본 동작 막기

    const targetElement = event.target.closest('.place-box');
    if (!targetElement || targetElement === selectedElement) return; // 유효하지 않은 타겟이면 무시

    const targetElementIndex = [...targetElement.parentNode.children].indexOf(targetElement);

    // 애니메이션을 위해 먼저 위치 변경
    if (targetElementIndex > selectedElementIndex) {
        targetElement.after(selectedElement);
    } else {
        targetElement.before(selectedElement);
    }

    // 애니메이션 리셋 후 초기화
    requestAnimationFrame(() => {
        selectedElement.classList.remove('dragging');
    });
}

function changePosition(targetElement) {
    if (!targetElement) {
        console.error("targetElement is not defined.");
        return;
    }

    const targetElementTop = targetElement.getBoundingClientRect().top;
    const selectedElementTop = selectedElement.getBoundingClientRect().top;

    const distance = targetElementTop - selectedElementTop;

    // 애니메이션을 통해 두 요소가 움직이는 것을 시각적으로 표현
    selectedElement.style.transform = `translateY(${distance}px)`;
    targetElement.style.transform = `translateY(${-distance}px)`;

    console.log(`Animating: ${selectedElement.id} moving ${distance}px`); // 로그 추가

    // 애니메이션 적용
    addAnimation(selectedElement);
    addAnimation(targetElement);

    // 실제 위치 변경은 setTimeout으로 애니메이션이 완료된 후 수행
    setTimeout(() => {
        if (distance > 0) {
            targetElement.after(selectedElement);
            console.log(`Changed position: ${selectedElement.id} after ${targetElement.id}`); // 로그 추가
        } else {
            targetElement.before(selectedElement);
            console.log(`Changed position: ${selectedElement.id} before ${targetElement.id}`); // 로그 추가
        }

        // 위치 변경 후 transform 속성을 초기화
        selectedElement.style.transform = '';
        targetElement.style.transform = '';
    }, 300); // 애니메이션 지속 시간
}

function onDragEnter(event) {
    const li = event.target.closest('.place-box'); // 현재 마우스가 있는 리스트 아이템

    if (li !== selectedElement && li.classList.contains('place-box')) {
        changePosition(li); // 드래그된 요소와 마우스가 있는 요소의 위치 변경
    }
}


function addAnimation(element) {
    element.classList.add('animation'); // 애니메이션 적용
    console.log(`Animation applied to: ${element.id}`); // 로그 추가
}targetElementTop = targetElement.getBoundingClientRect().top;
    const selectedElementTop = selectedElement.getBoundingClientRect().top;



// 드래그 이벤트 리스너 등록
document.querySelectorAll('.place-box').forEach(placeBox => {
    placeBox.addEventListener('dragstart', onDragStart);
    placeBox.addEventListener('dragover', onDragOver);
    placeBox.addEventListener('drop', onDrop);
});

function onDragEnter(event) {
    const li = event.target; // 현재 마우스가 있는 리스트 아이템

    if (li !== selectedElement && li.classList.contains('place-box')) {
        changePosition(li); // 드래그된 요소와 마우스가 있는 요소의 위치 변경
    }
}
