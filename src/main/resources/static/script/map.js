// 지도 및 관련 변수 초기화
var mapContainer = document.getElementById('map'),
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 8
    };

var map = new kakao.maps.Map(mapContainer, mapOption);
var scheduleRoutes = {}; // 일정별 경로 저장 객체
var routeColors = {}; // 일정별 색상 저장
var polylines = {}; // 일정별 폴리라인 저장
var markers = {}; // 일정별 마커 저장
var favoritePlaces = {}; // 관심 관광지 정보 저장
var currentPathPolyline = null; // 현재 경로를 저장하는 객체

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
    placeBox.setAttribute('ondragstart', drag); // 드래그 시작 시 drag 함수 호출
    placeBox.id = `place-${touristDetail.contentid}`; // 올바른 ID 설정

    const imageUrl = touristDetail.firstimage || '/images/placeholder.png';
    const title = touristDetail.title || "제목 없음";
    const address = touristDetail.addr1 || "주소 없음";

    placeBox.innerHTML = `
        <div class="place-info">
            <img src="${imageUrl}" alt="${title}" class="place-image" />
            <div class="place-text">
                <h4>${title}</h4>
                <p>${address}</p>
                <button onclick="showDetails('${touristDetail.contentid}')">상세정보</button>
            </div>
        </div>
    `;

    savedPlaces.appendChild(placeBox);
}

// 상세정보 모달창 표시
function showDetails(contentId) {
    fetch(`/touristSpot/Json/tourist-information?contentId=${contentId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('place-info').innerHTML = data.description;
            document.getElementById('search-result-modal').style.display = "block";
        });
}

// 마커 추가 및 경로 그리기
function addMarkerAndRoute(touristDetail, schedule) {
    const coords = new kakao.maps.LatLng(touristDetail.mapy, touristDetail.mapx);

    const marker = new kakao.maps.Marker({
        map: map,
        position: coords,
        title: `${markers[schedule]?.length + 1}` // 순서대로 마커에 번호 표시
    });

    const infowindow = new kakao.maps.InfoWindow({
        content: `<div style="padding:5px;">${touristDetail.title}</div>`
    });

    infowindow.open(map, marker);

    if (!markers[schedule]) markers[schedule] = [];
    markers[schedule].push(marker);

    if (!scheduleRoutes[schedule]) {
        scheduleRoutes[schedule] = [];
        routeColors[schedule] = getRandomColor();
    }

    scheduleRoutes[schedule].push(coords);

    if (scheduleRoutes[schedule].length > 1) {
        const startPoint = scheduleRoutes[schedule][scheduleRoutes[schedule].length - 2];
        const endPoint = scheduleRoutes[schedule][scheduleRoutes[schedule].length - 1];

        // 자동차 경로를 그리기 위해 호출
        getCarDirection({
            startPoint: {
                lat: startPoint.getLat(),
                lng: startPoint.getLng()
            },
            endPoint: {
                lat: endPoint.getLat(),
                lng: endPoint.getLng()
            }
        });
    }
}

// 자동차 경로를 가져오는 함수 (카카오 내비 API)
async function getCarDirection(pointObj) {
    const REST_API_KEY = 'YOUR_REST_API_KEY'; // 카카오 REST API 키
    const url = 'https://apis-navi.kakaomobility.com/v1/directions';

    const origin = `${pointObj.startPoint.lng},${pointObj.startPoint.lat}`;
    const destination = `${pointObj.endPoint.lng},${pointObj.endPoint.lat}`;

    const headers = {
        Authorization: `KakaoAK ${REST_API_KEY}`,
        'Content-Type': 'application/json'
    };

    const queryParams = new URLSearchParams({
        origin: origin,
        destination: destination
    });

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

        const linePath = [];
        data.routes[0].sections[0].roads.forEach(road => {
            road.vertexes.forEach((vertex, index) => {
                if (index % 2 === 0) {
                    linePath.push(new kakao.maps.LatLng(road.vertexes[index + 1], road.vertexes[index]));
                }
            });
        });

        if (currentPathPolyline) {
            currentPathPolyline.setMap(null); // 기존 경로 제거
        }

        currentPathPolyline = new kakao.maps.Polyline({
            path: linePath,
            strokeWeight: 5,
            strokeColor: '#000000',
            strokeOpacity: 0.7,
            strokeStyle: 'solid'
        });

        currentPathPolyline.setMap(map);
    } catch (error) {
        console.error('Error:', error);
    }
}

// 드래그 앤 드롭 기능
function allowDrop(ev) {
    ev.preventDefault(); // 기본 동작을 막아 드롭 가능하도록 설정
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id); // 드래그된 요소의 ID 저장
    console.log(`Dragging ID: ${ev.target.id}`); // 디버그용 로그 추가
}

function drop(ev) {
    ev.preventDefault(); // 기본 동작을 막음
    const data = ev.dataTransfer.getData("text"); // 드래그된 요소의 ID 가져옴
    console.log(`Dropped data: ${data}`); // 디버그용 로그 추가

    const targetContainer = ev.target;

    // 드롭 가능한 영역인 경우만 동작하도록 조건 추가
    if (targetContainer.classList.contains('drop-area')) {
        const placeElement = document.getElementById(data);
        if (placeElement) {
            console.log("Element found:", placeElement); // 디버그용 로그
            targetContainer.appendChild(placeElement); // 드래그된 요소를 드롭된 영역에 추가

            const schedule = targetContainer.closest('.schedule')?.id; // 드롭된 일정 구분
            if (schedule) {
                const placeId = data.split('-')[1];

                fetch(`/touristSpot/Json/tourist-information?contentId=${placeId}&contentTypeId=${favoritePlaces[placeId]}`)
                    .then(response => response.json())
                    .then(data => {
                        addMarkerAndRoute(data.touristDetail, schedule);
                    });
            } else {
                console.error("Invalid schedule container"); // 오류 처리
            }
        } else {
            console.error("Invalid element ID:", data); // 요소가 없을 때의 오류 처리
        }
    }
}

// 경로 보기
function showRoute() {
    drawAllRoutes(); // 모든 경로를 다시 그리기
}

// 경로 지우기
function clearRoute() {
    if (currentPathPolyline) {
        currentPathPolyline.setMap(null); // 지도에서 경로 제거
    }
}

// 랜덤 색상 생성 함수
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) color += letters[Math.floor(Math.random() * 16)];
    return color;
}

// 페이지 로드 시 저장된 관광지 목록 불러오기
document.addEventListener('DOMContentLoaded', function () {
    loadSavedPlaces();
});

// 드래그 가능한 요소에 드래그 이벤트 추가
document.querySelectorAll('.place-box').forEach(placeBox => {
    placeBox.addEventListener('dragstart', drag);
});

// 드롭 가능 영역에 드롭 이벤트 추가
document.querySelectorAll('.drop-area').forEach(dropArea => {
    dropArea.addEventListener('dragover', allowDrop);
    dropArea.addEventListener('drop', drop);
});
