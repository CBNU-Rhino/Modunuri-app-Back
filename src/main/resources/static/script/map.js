var mapContainer = document.getElementById('map'), // 지도 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 기본 중심 좌표
        level: 8 // 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 생성

var dayRoutes = {}; // 일차별 경로를 저장할 객체
var routeColors = {}; // 일차별 색상 저장
var polylines = {}; // 일차별 폴리라인을 저장할 객체
var markers = {};  // 각 일차의 마커를 저장할 객체
var favoritePlaces = {}; // 저장된 관심 관광지 정보 저장 객체

// 저장된 관광지 목록 불러오기
function loadSavedPlaces() {
    fetch('/users/getFavoriteContents')
        .then(response => response.json())
        .then(favoritePlacesData => {
            favoritePlaces = favoritePlacesData; // 데이터를 전역변수에 저장
            Object.keys(favoritePlaces).forEach(contentId => {
                const contentTypeId = favoritePlaces[contentId];  // contentTypeId를 함께 가져옴

                // contentId와 contentTypeId를 사용해 관광지 정보 가져오기
                fetch(`/touristSpot/Json/tourist-information?contentId=${contentId}&contentTypeId=${contentTypeId}`)
                    .then(response => response.json())
                    .then(data => {
                        displaySavedPlace(data.touristDetail);  // 관광지 정보를 UI에 표시하는 함수 호출
                    })
                    .catch(error => console.error("Error fetching tourist information:", error));
            });
        })
        .catch(error => {
            console.error('Error loading saved places:', error);
        });
}

// 저장된 관광지 정보를 박스로 표시
function displaySavedPlace(touristDetail) {
    const savedPlaces = document.querySelector('#saved-places');

    // 관광지 박스 생성
    const placeBox = document.createElement('div');
    placeBox.classList.add('place-box');
    placeBox.setAttribute('draggable', 'true');
    placeBox.setAttribute('ondragstart', 'drag(event)');
    placeBox.id = `place-${touristDetail.contentid}`;

    // 이미지와 텍스트 추가
    const imageUrl = touristDetail.firstimage || '/images/placeholder.png';  // 이미지가 없을 때 대체 이미지 사용
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

    // 관광지 목록에 추가
    savedPlaces.appendChild(placeBox);
}

// 마커 추가 및 경로 그리기
function addMarkerAndRoute(touristDetail, day) {
    const coords = new kakao.maps.LatLng(touristDetail.mapy, touristDetail.mapx);

    // 마커 생성 및 지도에 추가
    const marker = new kakao.maps.Marker({
        map: map,
        position: coords
    });

    // 인포윈도우 생성
    const infowindow = new kakao.maps.InfoWindow({
        content: `<div style="padding:5px;">${touristDetail.title}</div>` // 관광지 이름 표시
    });

    infowindow.open(map, marker);

    // 일차별 마커 관리
    if (!markers[day]) {
        markers[day] = [];
    }
    markers[day].push(marker);  // 마커를 해당 일차에 저장

    // 경로 관리
    if (!dayRoutes[day]) {
        dayRoutes[day] = [];
        routeColors[day] = getRandomColor();  // 일차별 색상 설정
    }
    dayRoutes[day].push(coords);  // 해당 일차에 좌표 추가

    drawRoute(day);  // 경로 그리기 함수 호출
}

// 모든 경로 리셋
function resetAllRoutes() {
    // 모든 폴리라인 제거
    Object.keys(polylines).forEach(day => {
        if (polylines[day]) {
            polylines[day].setMap(null);
        }
    });
    polylines = {}; // 초기화

    // 모든 마커 제거
    Object.keys(markers).forEach(day => {
        markers[day].forEach(marker => marker.setMap(null));
    });
    markers = {}; // 초기화
}

// 모든 일차의 경로를 다시 그리는 함수
function drawAllRoutes() {
    Object.keys(dayRoutes).forEach(day => {
        drawRoute(day); // 각 일차의 경로를 다시 그립니다
    });
}

// 경로를 그리기 위한 함수 (기존 경로 제거 후 새로 그리기)
function drawRoute(day) {
    // 해당 일차의 기존 폴리라인 삭제
    if (polylines[day]) {
        polylines[day].setMap(null); // 지도에서 해당 폴리라인을 제거
        delete polylines[day]; // 폴리라인 객체 삭제
    }

    // 해당 일차에 경로가 없으면 그리지 않음
    if (!dayRoutes[day] || dayRoutes[day].length === 0) {
        return;
    }

    // 새로운 경로로 폴리라인을 그리기
    const linePath = dayRoutes[day].map(coord => coord);

    const polyline = new kakao.maps.Polyline({
        path: linePath,
        strokeWeight: 5,
        strokeColor: routeColors[day], // 일차별로 설정된 색상 사용
        strokeOpacity: 0.7,
        strokeStyle: 'solid'
    });

    polyline.setMap(map);  // 새 폴리라인을 지도에 그리기
    polylines[day] = polyline; // 해당 일차의 폴리라인을 저장
}

// 마커와 경로 삭제
function removeMarkerAndRoute(markerToRemove, day) {
    // 마커 삭제
    markerToRemove.setMap(null);

    // 해당 마커의 좌표와 일치하는 경로를 삭제
    const markerPos = markerToRemove.getPosition();
    const routeIndex = dayRoutes[day].findIndex(coords => {
        return coords.getLat().toPrecision(14) === markerPos.getLat().toPrecision(14) &&
            coords.getLng().toPrecision(14) === markerPos.getLng().toPrecision(14);
    });

    if (routeIndex !== -1) {
        dayRoutes[day].splice(routeIndex, 1);  // 해당 좌표 경로 삭제
    }

    // 경로 새로 그리기
    drawRoute(day);
}

// 드래그 앤 드롭 기능
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

// 드래그 앤 드롭 기능
function drop(ev) {
    ev.preventDefault();
    const data = ev.dataTransfer.getData("text");
    const targetContainer = ev.target;

    // 드롭할 수 있는 영역인지 확인
    if (targetContainer.classList.contains('drop-area')) {
        const placeElement = document.getElementById(data);
        targetContainer.appendChild(placeElement);

        // 일차와 장소 정보 가져오기
        const day = targetContainer.closest('.day').id;
        const placeId = data.split('-')[1];

        // 기존 경로 모두 삭제
        resetAllRoutes();

        // 기존 일차에서 해당 장소 제거 (기존 경로 및 마커 제거)
        Object.keys(dayRoutes).forEach(dayKey => {
            dayRoutes[dayKey] = dayRoutes[dayKey].filter(
                (coord) => coord.contentId !== placeId
            );
        });

        // 새로운 일차에 장소 추가
        fetch(`/touristSpot/Json/tourist-information?contentId=${placeId}&contentTypeId=${favoritePlaces[placeId]}`)
            .then(response => response.json())
            .then(data => {
                addMarkerAndRoute(data.touristDetail, day);  // 새로운 마커 추가 및 경로 업데이트
                drawAllRoutes(); // 모든 경로 다시 그리기
            });
    }
}

// 랜덤 색상 생성 함수
function getRandomColor() {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
        color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
}

// 일차 추가 기능
var dayCount = 1;
function addDay() {
    dayCount++;
    var dayDiv = document.createElement("div");
    dayDiv.id = "day" + dayCount;
    dayDiv.className = "day";
    dayDiv.innerHTML = `<h3>${dayCount}일차</h3><div class="drop-area" ondrop="drop(event)" ondragover="allowDrop(event)"></div>`;
    document.querySelector('.days').appendChild(dayDiv);
}

// 페이지 로드 시 저장된 관광지 목록 불러오기
document.addEventListener('DOMContentLoaded', function () {
    loadSavedPlaces();
});

// 모달 닫기 기능
function closeModal() {
    document.getElementById('search-result-modal').style.display = "none";
}
